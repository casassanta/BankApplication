package com.gcc.bankapplication.model

import com.gcc.bankapplication.controller.response.AddressResponse
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.model.enums.Nationalities
import java.util.*
import javax.persistence.*

@Entity
data class Customer(

    @Id
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val birthDate: Date,

    @Enumerated(EnumType.STRING)
    val nationality: Nationalities,
    val document: Document
) {

    fun toCustomerResponse(addresses: List<AddressResponse>): CustomerResponse{
        return CustomerResponse(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            birthDate = this.birthDate,
            nationality =  this.nationality,
            document = this.document.toDocumentResponse(),
            addresses = addresses
        )
    }
}