package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.AddressType
import java.util.*
import javax.validation.constraints.Pattern

data class PostAddressRequest(

    val type: AddressType,

    @field:Pattern( regexp = "^[a-zA-Z0-9 ]+\$", message = "Invalid characters for address.postCode.")
    val postCode: String,

    @field:Pattern( regexp = "^[a-zA-Z0-9 ]+\$", message = "Invalid characters for address.address.")
    val address: String,

    @field:Pattern( regexp = "^[0-9 ]+\$", message = "Invalid characters for address.number.")
    val number: String,

    @field:Pattern( regexp = "^[a-zA-Z0-9 ]+\$", message = "Invalid characters for address.complement.")
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