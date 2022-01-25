package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.Nationalities
import java.util.*

data class CustomerModel(
    val id: UUID = UUID.randomUUID(),
    val firstName: String,
    val lastName: String,
    val birth_date: Date,
    val nationality: Nationalities,
    val document: DocumentModel,
) {
}