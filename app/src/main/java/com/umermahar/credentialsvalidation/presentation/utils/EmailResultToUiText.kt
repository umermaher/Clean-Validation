package com.umermahar.credentialsvalidation.presentation.utils

import com.umermahar.credentialsvalidation.R
import com.umermahar.credentialsvalidation.domain.utils.InputResult

fun emailResultToUiText(inputResult: InputResult): UiText? {
    val res = when (inputResult) {
        InputResult.EMPTY -> R.string.email_cant_be_blank
        InputResult.INVALID -> R.string.thats_not_a_valid_email
        InputResult.VALID -> null
    }
    return res?.let { UiText.StringResource(it) }
}