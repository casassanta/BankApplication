package com.gcc.bankapplication.validation

import com.gcc.bankapplication.model.Address
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class AddressTypeValidator: ConstraintValidator<AddressTypeValidation, String> {

    override fun isValid(addressType: String, context: ConstraintValidatorContext): Boolean {

        return Address.Type.values().any { address -> address.name == addressType}
    }
}