package com.gcc.bankapplication.model

import com.gcc.bankapplication.controller.response.AddressResponse
import java.util.*
import javax.persistence.*

@Entity
data class Address(

    @Id
    val id: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    val type: Type,
    val postCode: String,
    val address: String,
    val number: String,
    val complement: String? = null,

    @ManyToOne
    val customer: Customer
){

    enum class Type {
        DELIVERY,
        BILLING
    }

    fun toAddressResponse(): AddressResponse{
        return AddressResponse(
            id = this.id,
            type = this.type,
            postCode = this.postCode,
            address = this.address,
            number = this.number,
            complement = this.complement
        )
    }
}
