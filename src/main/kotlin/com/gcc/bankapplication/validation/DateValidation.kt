package com.gcc.bankapplication.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DateValidator::class])
@MustBeDocumented
annotation class DateValidation(
    val message: String = "Please, the date must be valid and age must be +18",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)