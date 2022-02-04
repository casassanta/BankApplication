package com.gcc.bankapplication.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationality
import com.gcc.bankapplication.validation.DateValidation
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

data class UpdateCustomerRequest(

    @field:Pattern( regexp = "^[a-zA-Z]+\$", message = "Invalid characters.")
    val firstName: String,

    @field:Pattern( regexp = "^[a-zA-Z]+\$", message = "Invalid characters.")
    val lastName: String,

    @field:Email
    val email: String,

    val nationality: Nationality,

    @field:Valid
    val document: UpdateDocumentRequest,

    @JsonAlias("birthdate")
    @DateValidation
    val birthDate: String,

    @field:Valid
    val addresses: List<UpdateAddressRequest>
) {

    fun toCustomerModel(customerId: UUID, customerStatus: Customer.Status): Customer {
        return Customer(
            id = customerId,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            birthDate = LocalDate.parse(this.birthDate),
            nationality = this.nationality,
            document = this.document.toDocument(),
            status = customerStatus
        )
    }

}