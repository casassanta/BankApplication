package com.gcc.bankapplication.controller.request

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.validation.AddressTypeValidation
import java.util.*
import javax.validation.constraints.NotBlank

data class UpdateAddressRequest(

    @field:AddressTypeValidation
    val type: String,

    @field:NotBlank
    val postCode: String,

    @field:NotBlank
    val address: String,

    @field:NotBlank
    val number: String,

    val complement: String? = null,

    ) {

    fun toAddress(addressId: UUID, customer: Customer): Address{

        return Address(
            id = addressId,
            type = Address.Type.valueOf(this.type),
            postCode = this.postCode,
            address = this.address,
            number = this .number,
            complement = this.complement,
            customer = customer
        )
    }

}