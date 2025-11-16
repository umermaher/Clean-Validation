package com.umermahar.credentialsvalidation.domain

import com.umermahar.credentialsvalidation.domain.ValidatePasswordRequirementsUseCase.RequirementType

class ValidatePasswordRequirementsUseCase {
    operator fun invoke(password: String): List<PasswordRequirement> {
        val hasMinLength = password.length >= 8
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }

        return listOf(
            PasswordRequirement(
                type = RequirementType.AT_LEAST_EIGHT_CHARACTERS,
                isFulfilled = hasMinLength
            ),
            PasswordRequirement(
                type = RequirementType.AT_LEAST_ONE_DIGIT,
                isFulfilled = hasDigit
            ),
            PasswordRequirement(
                type = RequirementType.AT_LEAST_ONE_SPECIAL,
                isFulfilled = hasSpecial
            ),
            PasswordRequirement(
                type = RequirementType.AT_LEAST_ONE_CAPITAL,
                isFulfilled = hasUpperCase
            ),
            PasswordRequirement(
                type = RequirementType.AT_LEAST_ONE_LOWERCASE,
                isFulfilled = hasLowerCase
            )
        )
    }

    fun getPasswordRequirements() = this("")

    enum class RequirementType {
        AT_LEAST_EIGHT_CHARACTERS, AT_LEAST_ONE_DIGIT, AT_LEAST_ONE_SPECIAL, AT_LEAST_ONE_CAPITAL, AT_LEAST_ONE_LOWERCASE
    }
}

data class PasswordRequirement(
    val type: RequirementType,
    val isFulfilled: Boolean
)