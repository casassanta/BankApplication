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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (type != other.type) return false
        if (postCode != other.postCode) return false
        if (address != other.address) return false
        if (number != other.number) return false
        if (complement != other.complement) return false
        if (customer != other.customer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + postCode.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + (complement?.hashCode() ?: 0)
        result = 31 * result + customer.hashCode()
        return result
    }
}
