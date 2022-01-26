package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.Nationalities
import java.util.*
import javax.persistence.*

@Entity
data class CustomerModel(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column
    val firstName: String,

    @Column
    val lastName: String,

    @Column
    val birth_date: Date,

    @Column
    val nationality: Nationalities,

    @Embedded
    val document: DocumentModel
) {
}