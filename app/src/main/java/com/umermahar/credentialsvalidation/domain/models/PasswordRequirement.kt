package com.umermahar.credentialsvalidation.domain.models

data class PasswordRequirement(
    val type: RequirementType,
    val isFulfilled: Boolean
)

enum class RequirementType {
    AT_LEAST_EIGHT_CHARACTERS, AT_LEAST_ONE_DIGIT, AT_LEAST_ONE_SPECIAL, AT_LEAST_ONE_CAPITAL, AT_LEAST_ONE_LOWERCASE
}