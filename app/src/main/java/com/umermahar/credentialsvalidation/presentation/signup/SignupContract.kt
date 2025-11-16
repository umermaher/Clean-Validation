package com.umermahar.credentialsvalidation.presentation.signup

import com.umermahar.credentialsvalidation.domain.PasswordRequirement
import com.umermahar.credentialsvalidation.presentation.utils.UiText

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val hasEmailFocusedOnce: Boolean = false,
    val hasPasswordFocusedOnce: Boolean = false,
    val focusedField: SignUpField? = null,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val isPasswordVisible: Boolean = false,
    val passwordRequirements: List<PasswordRequirement> = emptyList(),
    val isSignUpButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)

enum class SignUpField {
    EMAIL, PASSWORD
}

sealed class SignUpEvent {
    data class OnEmailChanged(val value: String): SignUpEvent()
    data class OnPasswordChanged(val value: String): SignUpEvent()
    data class OnFocusChange(val field: SignUpField, val isFocused: Boolean): SignUpEvent()
    data class OnPasswordVisibilityChanged(val isPasswordVisible: Boolean): SignUpEvent()
    data object OnSignUpButtonClick: SignUpEvent()
}

sealed interface SignUpResult {
    data object SignUpFailed: SignUpResult
}