package com.gcc.bankapplication.utils

import com.gcc.bankapplication.controller.response.AddressResponse
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.controller.response.DocumentResponse
import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.Document
import com.gcc.bankapplication.model.enums.Nationality
import java.time.LocalDate
import java.util.*

object ObjectMother {

    fun getCustomer(id: UUID = UUID.randomUUID()): Customer {
        return Customer(
            id = id,
            firstName = "Gui",
            lastName = "Cas",
            email ="a@b.c",
            birthDate = LocalDate.parse("2021-01-01"),
            nationality = Nationality.BRAZIL,
            document = Document(Document.Type.ID, "123"),
            status = Customer.Status.ACTIVE
        )
    }

    fun getBillingAddress(customer: Customer): Address {
        return Address(
            id = UUID.randomUUID(),
            type = Address.Type.BILLING,
            postCode = "1",
            address = "1",
            number = "1",
            complement = "1",
            customer = customer
        )
    }

    fun getDeliveryAddress(customer: Customer): Address {
        return Address(
            id = UUID.randomUUID(),
            type = Address.Type.DELIVERY,
            postCode = "2",
            address = "2",
            number = "2",
            complement = "2",
            customer = customer
        )
    }

    fun getCustomerResponse(customer: Customer, addresses: List<Address>): CustomerResponse{
        return CustomerResponse(
            id = customer.id,
            firstName = customer.firstName,
            lastName = customer.lastName,
            email = customer.email,
            birthDate = customer.birthDate,
            nationality = customer.nationality,
            document = DocumentResponse(customer.document.type, customer.document.number),
            addresses = listOf(
                AddressResponse(
                    id = addresses[0].id,
                    type = addresses[0].type,
                    postCode = addresses[0].postCode,
                    address = addresses[0].address,
                    number = addresses[0].number,
                    complement = addresses[0].complement
                ),
                AddressResponse(
                    id = addresses[1].id,
                    type = addresses[1].type,
                    postCode = addresses[1].postCode,
                    address = addresses[1].address,
                    number = addresses[1].number,
                    complement = addresses[1].complement
                )
            )
        )
    }

}