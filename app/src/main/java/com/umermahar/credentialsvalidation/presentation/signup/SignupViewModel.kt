package com.umermahar.credentialsvalidation.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umermahar.credentialsvalidation.domain.ValidateEmailUseCase
import com.umermahar.credentialsvalidation.domain.ValidatePasswordRequirementsUseCase
import com.umermahar.credentialsvalidation.domain.ValidatePasswordUseCase
import com.umermahar.credentialsvalidation.domain.utils.InputResult
import com.umermahar.credentialsvalidation.presentation.utils.emailResultToUiText
import com.umermahar.credentialsvalidation.presentation.utils.passwordResultToUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import com.umermahar.credentialsvalidation.domain.utils.Result
import kotlinx.coroutines.launch

class SignupViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validatePasswordRequirementsUseCase: ValidatePasswordRequirementsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SignUpState(
        passwordRequirements = validatePasswordRequirementsUseCase.getPasswordRequirements()
    ))
    val state = _state.asStateFlow()

    private val resultChannel = Channel<SignUpResult>()
    val signUpResult = resultChannel.receiveAsFlow()

    init {

        state.distinctUntilChangedBy { it.email }
            .map { it.email }
            .onEach { email ->
                val inputResult = validateEmailUseCase(email)
                val emailErrorRes = emailResultToUiText(inputResult)
                _state.update { it.copy(
                    emailError = emailErrorRes,
                    isSignUpButtonEnabled = inputResult == InputResult.VALID && it.passwordError == null && it.hasPasswordFocusedOnce
                ) }
            }
            .launchIn(viewModelScope)

        state.distinctUntilChangedBy { it.password }
            .map { it.password }
            .onEach { password ->
                val inputResult = validatePasswordUseCase(password)
                val passwordErrorRes = when(inputResult) {
                    is Result.Error -> passwordResultToUiText(inputResult.error)
                    is Result.Success -> null
                }

                _state.update { it.copy(
                    passwordError = passwordErrorRes,
                    passwordRequirements = validatePasswordRequirementsUseCase(password),
                    isSignUpButtonEnabled = inputResult is Result.Success && it.emailError == null && it.hasEmailFocusedOnce
                ) }
            }
            .launchIn(viewModelScope)

    }

    /**
     * [onEvent] handles SignUp events
     * @param event of type [SignUpEvent] related to signUp events
     * **/
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailChanged -> _state.update {
                it.copy(email = event.value)
            }
            is SignUpEvent.OnPasswordChanged -> _state.update {
                it.copy(password = event.value)
            }
            is SignUpEvent.OnFocusChange -> _state.update { state ->
                when (event.field) {
                    SignUpField.EMAIL -> state.copy(
                        hasEmailFocusedOnce = state.hasEmailFocusedOnce || event.isFocused,
                        focusedField = if (event.isFocused) event.field else null
                    )
                    SignUpField.PASSWORD -> state.copy(
                        hasPasswordFocusedOnce = state.hasPasswordFocusedOnce || event.isFocused,
                        focusedField = if (event.isFocused) event.field else null
                    )
                }
            }
            is SignUpEvent.OnPasswordVisibilityChanged -> TODO()
            SignUpEvent.OnSignUpButtonClick -> signUp()
        }
    }

    private fun signUp() = viewModelScope.launch {
        _state.update { it.copy() }
        if(state.value.email == "jane@gmail.com") {
            resultChannel.send(SignUpResult.SignUpFailed)
        } else {
            resultChannel.send(SignUpResult.SignUpFailed)
        }
    }

}