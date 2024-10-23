package cz.moro.usermanagment.validator

import cz.moro.usermanagment.annotations.ValidPassword
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String?> {

    override fun isValid(password: String?, context: ConstraintValidatorContext): Boolean {
        if (password == null) {
            return true
        }

        return if (password.isBlank()) {
            false
        } else {
            // Custom password rules
            password.length >= 8 && // Minimum length
            password.any { it.isDigit() } && // At least one digit
            password.any { it.isLowerCase() } && // At least one lowercase letter
            password.any { it.isUpperCase() } && // At least one uppercase letter
            password.any { "!@#$%^&+=*()_".contains(it) } // At least one special character
        }
    }
}
