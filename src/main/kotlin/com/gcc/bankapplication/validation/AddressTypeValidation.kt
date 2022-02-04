package com.gcc.bankapplication.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AddressTypeValidator::class])
@MustBeDocumented
annotation class AddressTypeValidation(
    val message: String = "Address Type must be BILLING or DELIVERY",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
