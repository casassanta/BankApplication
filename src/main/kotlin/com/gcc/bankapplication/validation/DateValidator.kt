package com.gcc.bankapplication.validation

import java.time.LocalDate
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DateValidator : ConstraintValidator<DateValidation, LocalDate> {

    override fun isValid(date: LocalDate, context: ConstraintValidatorContext?): Boolean {

        val dateNow = LocalDate.now()

        if(dateNow.year - date.year > 18) {
            return true
        }else if (dateNow.year - date.year == 18) {
            if(date.monthValue < dateNow.monthValue)
                return true
            else if(date.monthValue == dateNow.monthValue) {
                return date.dayOfMonth <= dateNow.dayOfMonth
            }
        }

        return false
    }
}