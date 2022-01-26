package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.AddressType
import java.util.*

data class PostAddressRequest(

    val type: AddressType,
    val postCode: String,
    val address: String,
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