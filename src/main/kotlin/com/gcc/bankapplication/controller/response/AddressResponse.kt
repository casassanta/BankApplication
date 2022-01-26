package com.gcc.bankapplication.controller.response

import com.gcc.bankapplication.model.enums.AddressType
import java.util.*

data class AddressResponse(
    val id: UUID,
    val type: AddressType,
    val postCode: String,
    val address: String,
    val number: String,
    val complement: String? = null
) {
}