package com.umermahar.credentialsvalidation.domain

interface EmailPatternValidator {
    fun isValidEmail(email: String): Boolean
}