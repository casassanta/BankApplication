package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.Document
import com.gcc.bankapplication.model.enums.Nationality
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DateTimeException
import java.time.LocalDate


class CreateCustomerRequestTest{

    private val createCustomerRequest = CreateCustomerRequest(
        firstName = "Guilherme",
        lastName = "Casassanta",
        nationality = Nationality.ALBANIA,
        document = CreateDocumentRequest(Document.Type.ID, "100"),
        birthDate = "1991-12-10",
        addresses = listOf(
            CreateAddressRequest(
                type = Address.Type.BILLING,
                postCode =  "38408",
                number = "10",
                address = "Jose Miguel Saramago",
                complement = "Apto 101"
            ),
            CreateAddressRequest(
                type = Address.Type.DELIVERY,
                postCode =  "38408",
                number = "10",
                address = "Jose Miguel Saramago",
                complement = "Apto 401"
            )
        )
    )

    @Test
    fun `should map CreateCustomerRequest to Customer properly`(){
        val actualCustomer = createCustomerRequest.toCustomer()
        val expectedCustomer = Customer(
            id = actualCustomer.id,
            firstName = createCustomerRequest.firstName,
            lastName = createCustomerRequest.lastName,
            birthDate = LocalDate.parse(createCustomerRequest.birthDate),
            nationality = createCustomerRequest.nationality,
            document = Document(Document.Type.ID, "100")
        )

        assertEquals(expectedCustomer,actualCustomer)
    }

    @Test
    fun `should throw DateTimeException when birthDate is invalid`(){
        val invalidCustomerRequest = createCustomerRequest.copy(birthDate = "19911-10-12")

        assertThrows(DateTimeException::class.java) {invalidCustomerRequest.toCustomer()}
    }
}