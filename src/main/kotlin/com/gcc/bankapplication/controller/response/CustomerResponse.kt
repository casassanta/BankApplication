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

}