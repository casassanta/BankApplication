package com.gcc.bankapplication.controller.response

import com.gcc.bankapplication.controller.request.PostDocumentRequest
import com.gcc.bankapplication.model.enums.Nationalities
import java.util.*

data class CustomerResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val birthDate: Date,
    val nationality: Nationalities,
    val document: DocumentResponse,
    val addresses: List<AddressResponse>
) {

}