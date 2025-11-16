package com.umermahar.credentialsvalidation.data

import android.util.Patterns
import com.umermahar.credentialsvalidation.domain.EmailPatternValidator

class AndroidEmailPatternValidator: EmailPatternValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}