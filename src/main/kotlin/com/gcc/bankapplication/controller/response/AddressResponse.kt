package com.gcc.bankapplication.controller.response

import com.gcc.bankapplication.model.Address
import java.util.*

data class AddressResponse(
    val id: UUID,
    val type: Address.Type,
    val postCode: String,
    val address: String,
    val number: String,
    val complement: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddressResponse

        if (type != other.type) return false
        if (postCode != other.postCode) return false
        if (address != other.address) return false
        if (number != other.number) return false
        if (complement != other.complement) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + postCode.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + (complement?.hashCode() ?: 0)
        return result
    }
}