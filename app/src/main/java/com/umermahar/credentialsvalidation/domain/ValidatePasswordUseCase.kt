package com.umermahar.credentialsvalidation.domain

import com.umermahar.credentialsvalidation.domain.utils.Error
import com.umermahar.credentialsvalidation.domain.utils.Result

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Result<Unit, PasswordError> {

        if(password.isEmpty()) {
            return Result.Error(PasswordError.EMPTY)
        }

        // Check length
        if (password.length < 8) {
            return Result.Error(PasswordError.SHORT)
        }

        val containsDigit = password.any { it.isDigit() }

        if (!containsDigit) {
            return Result.Error(PasswordError.AT_LEAST_ONE_DIGIT)
        }

        // Check for special characters
        val containsSpecialChar = password.any { it.isLetterOrDigit().not() }
        if (!containsSpecialChar) {
            return Result.Error(PasswordError.AT_LEAST_ONE_SPECIAL)
        }

        // Check for at least one capital letter
        val containsCapitalLetter = password.any { it.isUpperCase() }
        if (!containsCapitalLetter) {
            return Result.Error(PasswordError.AT_LEAST_ONE_CAPITAL)
        }

        // Check for at least one lowercase letter
        val containsLowerCaseLetter = password.any { it.isLowerCase() }
        if (!containsLowerCaseLetter) {
            return Result.Error(PasswordError.AT_LEAST_ONE_LOWERCASE)
        }

        return Result.Success(Unit)
    }

    enum class PasswordError: Error {
        EMPTY, SHORT, AT_LEAST_ONE_DIGIT, AT_LEAST_ONE_SPECIAL, AT_LEAST_ONE_CAPITAL, AT_LEAST_ONE_LOWERCASE
    }
}