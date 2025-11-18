package com.umermahar.credentialsvalidation.presentation.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.umermahar.credentialsvalidation.R
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff

sealed class InputType (
    open val label: UiText,
    val icon:  @Composable () -> Unit,
    val trailingIcon: @Composable (() -> Unit)? = null,
    open val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation,
    val keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    data object Email: InputType(
        label = UiText.StringResource(R.string.email),
        icon = {
            Icon(
                imageVector = Icons.Rounded.Email,
                contentDescription = null,
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None
    )
    data class Password(
        override val label: UiText = UiText.StringResource(R.string.password),
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
        val isPasswordVisible: Boolean = false,
        val onPasswordVisibilityToggle: (Boolean) -> Unit = {},
        val onDone: (KeyboardActionScope.() -> Unit)? = null,
    ): InputType(
        label = label,
        icon = {
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = null,
            )
        },
        trailingIcon = {
            Box(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                onPasswordVisibilityToggle(true)
                                tryAwaitRelease() // Wait for the touch to end
                                onPasswordVisibilityToggle(false)
                            }
                        )
                    }
            ) {
                val image = if (isPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                val description = if (isPasswordVisible) "Hide password" else "Show password"

                Icon(imageVector = image, contentDescription = description)
            }
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onDone = onDone)
    )
}