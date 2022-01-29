package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.AddressType
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PostAddressRequest(

    val type: AddressType,

    @field:NotBlank
    val postCode: String,

    @field:NotBlank
    val address: String,

    @field:NotBlank
    val number: String,

    val complement: String? = null,

) {

    fun toAddress(customer: Customer): Address{
        return Address(
            id = UUID.randomUUID(),
            type = this.type,
            postCode = this.postCode,
            address = this.address,
            number = this .number,
            complement = this.complement,
            customer = customer
        )
    }
}