package com.gcc.bankapplication.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DocumentTypeValidator::class])
@MustBeDocumented
annotation class DocumentTypeValidation(
    val message: String = "The document type should be ID or PASSPORT",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)