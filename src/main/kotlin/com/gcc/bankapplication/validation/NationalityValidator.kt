package com.gcc.bankapplication.validation

import com.gcc.bankapplication.model.enums.Nationality
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NationalityValidator: ConstraintValidator<NationalityValidation, String> {

    override fun isValid(nationality: String, context: ConstraintValidatorContext): Boolean {
        return Nationality.values().any { nationalities -> nationalities.name == nationality}
    }
}