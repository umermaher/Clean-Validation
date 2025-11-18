package com.umermahar.credentialsvalidation.di

import com.umermahar.credentialsvalidation.data.AndroidEmailPatternValidator
import com.umermahar.credentialsvalidation.domain.EmailPatternValidator
import com.umermahar.credentialsvalidation.domain.ValidateEmailUseCase
import com.umermahar.credentialsvalidation.domain.ValidatePasswordRequirementsUseCase
import com.umermahar.credentialsvalidation.domain.ValidatePasswordUseCase
import com.umermahar.credentialsvalidation.presentation.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    factory<EmailPatternValidator> { AndroidEmailPatternValidator() }
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidatePasswordRequirementsUseCase)
    viewModelOf(::SignUpViewModel)
}