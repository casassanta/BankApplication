package com.gcc.bankapplication.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationalities
import java.util.*

data class PostCustomerRequest(
    val firstName: String,
    val lastName: String,
    @JsonAlias("birthdate")
    val birthDate: Date,
    val nationality: Nationalities,
    val document: PostDocumentRequest
) {

    fun toCustomer(): Customer {
        return Customer(
            id = UUID.randomUUID(),
            firstName = this.firstName,
            lastName = this.lastName,
            birthDate = this.birthDate,
            nationality = this.nationality,
            document = this.document.toDocument()
        )
    }
}