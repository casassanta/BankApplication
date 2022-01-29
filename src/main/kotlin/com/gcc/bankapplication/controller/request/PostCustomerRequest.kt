package com.gcc.bankapplication.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationalities
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Pattern


data class PostCustomerRequest(

    @field:Pattern( regexp = "^[a-zA-Z]+\$", message = "Invalid characters for firstName.")
    val firstName: String,

    @field:Pattern( regexp = "^[a-zA-Z]+\$", message = "Invalid characters for lastName.")
    val lastName: String,

    val nationality: Nationalities,

    @field:Valid
    val document: PostDocumentRequest,

    @JsonAlias("birthdate")
    val birthDate: LocalDate,

    @field:Valid
    val addresses: List<PostAddressRequest>
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