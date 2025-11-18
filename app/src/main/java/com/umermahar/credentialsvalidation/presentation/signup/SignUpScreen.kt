package com.umermahar.credentialsvalidation.presentation.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umermahar.credentialsvalidation.presentation.utils.ShakingState
import com.umermahar.credentialsvalidation.presentation.utils.rememberShakingState
import org.koin.androidx.compose.koinViewModel
import com.umermahar.credentialsvalidation.R
import com.umermahar.credentialsvalidation.presentation.utils.InputType
import com.umermahar.credentialsvalidation.presentation.utils.UiText
import com.umermahar.credentialsvalidation.presentation.utils.composables.ActionButton
import com.umermahar.credentialsvalidation.presentation.utils.composables.ErrorText
import com.umermahar.credentialsvalidation.presentation.utils.composables.PasswordRequirements
import com.umermahar.credentialsvalidation.presentation.utils.composables.TextInputField

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val shakingState = rememberShakingState()

    LaunchedEffect(true) {
        viewModel.signUpResult.collect { result ->
            when(result) {
                SignUpResult.SignUpFailed -> shakingState.shake()
            }
        }
    }

    SignUpScreenContent(
        state = state,
        signUpButtonShakingState = shakingState,
        onEvent = viewModel::onEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    state: SignUpState,
    signUpButtonShakingState: ShakingState,
    onEvent: (SignUpEvent) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {  }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { pd ->

            Box(
                Modifier
                    .fillMaxSize()
                    .padding(pd)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.create_your_account),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )

                    Text(
                        text = stringResource(id = R.string.run_your_world_one_step_at_a_time),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextInputField(
                        inputType = InputType.Email,
                        value = state.email,
                        isError = state.emailError != null && state.hasEmailFocusedOnce,
                        isFocused = state.focusedField == SignUpField.EMAIL,
                        onFocusedChanged = { isFocused ->
                            onEvent( SignUpEvent.OnFocusChange(
                                field = SignUpField.EMAIL,
                                isFocused = isFocused
                            ) )
                        }
                    ) { value ->
                        onEvent(SignUpEvent.OnEmailChanged(value))
                    }

                    AnimatedVisibility(state.emailError != null && state.focusedField != SignUpField.EMAIL && state.hasEmailFocusedOnce) {
                        state.emailError?.let { ErrorText(uiText = it) }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextInputField(
                        inputType = InputType.Password(
                            onDone = {
                                focusManager.clearFocus()
                                if(state.isSignUpButtonEnabled) {
                                    onEvent(SignUpEvent.OnSignUpButtonClick)
                                }
                            },
                            isPasswordVisible = state.isPasswordVisible,
                            onPasswordVisibilityToggle = {
                                onEvent(SignUpEvent.OnPasswordVisibilityChanged(it))
                            }
                        ),
                        value = state.password,
                        isError = state.passwordError != null && state.hasPasswordFocusedOnce,
                        isFocused = state.focusedField == SignUpField.PASSWORD,
                        onFocusedChanged = { isFocused ->
                            onEvent( SignUpEvent.OnFocusChange(
                                field = SignUpField.PASSWORD,
                                isFocused = isFocused
                            ) )
                        }
                    ) { value ->
                        onEvent(SignUpEvent.OnPasswordChanged(value))
                    }

                    AnimatedVisibility(state.passwordError != null && state.focusedField != SignUpField.PASSWORD && state.hasPasswordFocusedOnce) {
                        state.passwordError?.let { ErrorText(uiText = it) }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ActionButton(
                        text = stringResource(id = R.string.create),
                        isEnabled = state.isSignUpButtonEnabled,
                        shakeState = signUpButtonShakingState,
                        onClick = {
                            onEvent(SignUpEvent.OnSignUpButtonClick)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.focusedField == SignUpField.PASSWORD,
            enter = fadeIn(
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            ) + expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            exit = fadeOut(
                animationSpec = tween(300, easing = FastOutLinearInEasing)
            ) + shrinkVertically()
        ) {

            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                PasswordRequirements(
                    requirements = state.passwordRequirements,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}