package com.gcc.bankapplication.model

import com.gcc.bankapplication.model.enums.AddressType
import java.util.*
import javax.persistence.*

@Entity
data class Address(

    @Id
    val id: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    val type: AddressType,
    val postCode: String,
    val address: String,
    val number: String,
    val complement: String? = null,

    @ManyToOne
    val customer: Customer
)