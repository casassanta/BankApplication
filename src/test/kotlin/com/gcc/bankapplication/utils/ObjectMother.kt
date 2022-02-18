package com.gcc.bankapplication.utils

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

}