package com.umermahar.credentialsvalidation.presentation.utils

import com.umermahar.credentialsvalidation.R
import com.umermahar.credentialsvalidation.domain.ValidatePasswordUseCase

fun passwordResultToUiText(result: ValidatePasswordUseCase.PasswordError): UiText {
    val msgRes = when (result) {
        ValidatePasswordUseCase.PasswordError.EMPTY -> R.string.password_cant_be_empty
        ValidatePasswordUseCase.PasswordError.SHORT -> R.string.password_is_too_short
        ValidatePasswordUseCase.PasswordError.AT_LEAST_ONE_SPECIAL -> R.string.one_special_character_msg
        ValidatePasswordUseCase.PasswordError.AT_LEAST_ONE_CAPITAL -> R.string.one_capital_letter_msg
        ValidatePasswordUseCase.PasswordError.AT_LEAST_ONE_DIGIT -> R.string.one_digit_msg
        ValidatePasswordUseCase.PasswordError.AT_LEAST_ONE_LOWERCASE -> R.string.one_lowercase_letter_msg
    }
    return UiText.StringResource(msgRes)
}