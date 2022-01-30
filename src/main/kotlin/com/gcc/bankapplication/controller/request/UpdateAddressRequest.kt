package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import javax.validation.constraints.NotBlank

data class UpdateAddressRequest(

    val type: Address.Type,

    @field:NotBlank
    val postCode: String,

    @field:NotBlank
    val address: String,

    @field:NotBlank
    val number: String,

    val complement: String? = null,

    ) {

    fun toAddress(previousAddress: List<Address>, customer:Customer): Address{

        val addressId = previousAddress.first { it.type == this.type}.id

        return Address(
            id = addressId,
            type = this.type,
            postCode = this.postCode,
            address = this.address,
            number = this .number,
            complement = this.complement,
            customer = customer
        )
    }

}