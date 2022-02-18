package com.gcc.bankapplication.controller.response

import com.gcc.bankapplication.model.enums.Nationality
import java.time.LocalDate
import java.util.*

data class CustomerResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: LocalDate,
    val nationality: Nationality,
    val document: DocumentResponse,
    val addresses: List<AddressResponse>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomerResponse

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (birthDate != other.birthDate) return false
        if (nationality != other.nationality) return false
        if (document != other.document) return false
        if (addresses != other.addresses) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + nationality.hashCode()
        result = 31 * result + document.hashCode()
        result = 31 * result + addresses.hashCode()
        return result
    }

}