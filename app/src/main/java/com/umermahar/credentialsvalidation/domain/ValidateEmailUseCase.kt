package com.umermahar.credentialsvalidation.domain

import com.umermahar.credentialsvalidation.domain.utils.InputResult

class ValidateEmailUseCase(
    private val emailPatternValidator: EmailPatternValidator
) {
    operator fun invoke(email: String): InputResult {
        return if(email.isEmpty()) {
            InputResult.EMPTY
        } else if(emailPatternValidator.isValidEmail(email = email)) {
            InputResult.VALID
        } else {
            InputResult.INVALID
        }
    }
}