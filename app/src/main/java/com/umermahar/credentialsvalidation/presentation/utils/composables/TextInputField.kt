package com.umermahar.credentialsvalidation.presentation.utils.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.umermahar.credentialsvalidation.presentation.utils.InputType

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    inputType: InputType,
    value: String,
    isError: Boolean,
    isFocused: Boolean = false,
    onFocusedChanged: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onFocusedChanged(focusState.isFocused)
            },
        leadingIcon = inputType.icon,
        trailingIcon = inputType.trailingIcon,
        label = {
            Text(text = inputType.label.asString())
        },
        isError = if(isFocused) false else isError,
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = inputType.keyboardActions
    )
}