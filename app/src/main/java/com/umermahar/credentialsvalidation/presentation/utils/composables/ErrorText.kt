package com.umermahar.credentialsvalidation.presentation.utils.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umermahar.credentialsvalidation.presentation.utils.UiText

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    uiText: UiText
) {
    Text(
        modifier = modifier
            .padding(top = 4.dp),
        text = uiText.asString(),
        style = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.error
        )
    )
}