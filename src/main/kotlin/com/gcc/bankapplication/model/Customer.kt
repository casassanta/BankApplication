package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.Nationalities
import java.util.*
import javax.persistence.*

@Entity
data class Customer(

    @Id
    val id: UUID = UUID.randomUUID(),

    val firstName: String,
    val lastName: String,
    val birthDate: Date,
    val nationality: Nationalities,
    val document: Document
) {
}