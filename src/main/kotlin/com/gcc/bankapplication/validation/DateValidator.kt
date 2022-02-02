package com.gcc.bankapplication.validation

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DateValidator : ConstraintValidator<DateValidation, String> {

    override fun isValid(date: String, context: ConstraintValidatorContext): Boolean {

        val datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd")

        return try{
            datePattern.parse(date)
            true
        }catch (e: DateTimeParseException) {
            false
        }

    }
}