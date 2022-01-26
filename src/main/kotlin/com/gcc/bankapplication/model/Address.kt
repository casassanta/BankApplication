package com.gcc.bankapplication.model

import java.util.*

data class Address(
    val id: UUID = UUID.randomUUID(),
    val postCode: String,
    val address: String,
    val number: String,
    val complement: String? = null,
    val customerId: Customer
)