package com.gcc.bankapplication.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NationalityValidator::class])
@MustBeDocumented
annotation class NationalityValidation(
    val message: String = "Invalid Nationality",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
