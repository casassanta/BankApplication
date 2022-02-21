package com.gcc.bankapplication.controller

import com.gcc.bankapplication.service.AddressService
import com.gcc.bankapplication.service.CustomerService
import com.gcc.bankapplication.utils.ObjectMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.UUID
import javax.persistence.EntityNotFoundException

@WebMvcTest(CustomerController::class)
class CustomerControllerTest{

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var customerService: CustomerService

    @MockkBean
    lateinit var addressService: AddressService

    @Test
    fun `should find customer by Id`(){

        val customerId = UUID.fromString("6bb00d56-893f-43e7-98e0-68157e852c9b")
        val customer = ObjectMother.getCustomer(customerId)
        val addresses = listOf(
            ObjectMother.getBillingAddress(customer).copy(id = UUID.fromString("6bb00d56-893f-43e7-98e0-68157e852c11")),
            ObjectMother.getDeliveryAddress(customer).copy(id = UUID.fromString("6bb00d56-893f-43e7-98e0-68157e852c22"))
        )

        every { customerService.findById(customerId) } returns customer
        every { addressService.findByCustomer(customer) } returns addresses

        this.mockMvc.perform(get("/api/customers/${customerId}"))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """
                    {
                        "id": "6bb00d56-893f-43e7-98e0-68157e852c9b",
                        "firstName": "Gui",
                        "lastName": "Cas",
                        "email": "a@b.c",
                        "birthDate": "2021-01-01",
                        "nationality": "BRAZIL",
                        "document": {
                            "type": "ID",
                            "number": "123"
                        },
                        "addresses": [
                            {
                                "id": "6bb00d56-893f-43e7-98e0-68157e852c11",
                                "type": "BILLING",
                                "postCode": "1",
                                "address": "1",
                                "number": "1",
                                "complement": "1"
                            },
                            {
                                "id": "6bb00d56-893f-43e7-98e0-68157e852c22",
                                "type": "DELIVERY",
                                "postCode": "2",
                                "address": "2",
                                "number": "2",
                                "complement": "2"
                            }
                        ]
                    }
                """.trimIndent()
            ))

    }

    @Test
    fun `should return 404 when customer is not found`(){

        val customerId = UUID.randomUUID()

        every { customerService.findById(customerId) } throws JpaObjectRetrievalFailureException(EntityNotFoundException())

        this.mockMvc.perform(get("/api/customers/${customerId}"))
            .andExpect(status().isNotFound)
            .andExpect(content().json(
                """
                    {
                        "message": "Object not found",
                        "errors": []
                    }
                """.trimIndent()
        ))

    }

}