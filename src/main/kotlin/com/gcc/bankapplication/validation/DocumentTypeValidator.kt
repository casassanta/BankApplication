package com.gcc.bankapplication.validation

import com.gcc.bankapplication.model.Document
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DocumentTypeValidator: ConstraintValidator<DocumentTypeValidation, String> {

    override fun isValid(documentType: String, context: ConstraintValidatorContext): Boolean {

        return Document.Type.values().any { type -> type.name == documentType}
    }
}