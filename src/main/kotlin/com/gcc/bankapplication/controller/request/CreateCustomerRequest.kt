package com.gcc.bankapplication.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationality
import com.gcc.bankapplication.validation.DateValidation
import com.gcc.bankapplication.validation.NationalityValidation
import java.lang.RuntimeException
import java.time.LocalDate
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern


data class CreateCustomerRequest(

    @field:Pattern( regexp = "^[a-zA-Z]+\$", message = "Invalid characters.")
    val firstName: String,

    @field:Pattern( regexp = "^[a-zA-Z]+\$", message = "Invalid characters.")
    val lastName: String,

    @field:NotNull(message = "Email missing")
    @field:Email
    val email: String,

    @field:NationalityValidation
    val nationality: String,

    @field:Valid
    val document: CreateDocumentRequest,

    @JsonAlias("birthdate")
    @DateValidation
    val birthDate: String,

    @field:Valid
    val addresses: List<CreateAddressRequest>
) {

    fun toCustomer(): Customer {
        return Customer(
            id = UUID.randomUUID(),
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            birthDate = LocalDate.parse(this.birthDate),
            nationality = Nationality.valueOf(this.nationality),
            document = this.document.toDocument()
        )
    }

}