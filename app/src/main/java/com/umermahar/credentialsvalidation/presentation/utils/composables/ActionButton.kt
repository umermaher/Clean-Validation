package com.umermahar.credentialsvalidation.presentation.utils.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.umermahar.credentialsvalidation.presentation.utils.ShakingState
import com.umermahar.credentialsvalidation.presentation.utils.shakable

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    shakeState: ShakingState? = null,
) {
    // Animate background color based on enabled state
    val backgroundColor by animateColorAsState(
        targetValue = if (isEnabled) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        },
        label = "buttonBackgroundAnimation"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isEnabled) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        },
        label = "buttonContentAnimation"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (shakeState != null) {
                    Modifier.shakable(shakeState)
                } else Modifier
            ),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor, // animate instead of static
            disabledContentColor = contentColor
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}