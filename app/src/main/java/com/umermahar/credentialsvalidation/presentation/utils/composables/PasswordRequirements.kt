package com.umermahar.credentialsvalidation.presentation.utils.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umermahar.credentialsvalidation.R
import com.umermahar.credentialsvalidation.domain.models.PasswordRequirement
import com.umermahar.credentialsvalidation.domain.models.RequirementType

@Composable
fun PasswordRequirements(
    requirements: List<PasswordRequirement>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.your_password_must_contain),
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        requirements.forEach { requirement ->
            PasswordRequirement(passwordRequirement = requirement)
        }
    }
}

@Composable
private fun PasswordRequirement(
    passwordRequirement: PasswordRequirement,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedCheckCross(
            isChecked = passwordRequirement.isFulfilled,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(
                id = when (passwordRequirement.type) {
                    RequirementType.AT_LEAST_EIGHT_CHARACTERS -> R.string.atleast_eight_characters
                    RequirementType.AT_LEAST_ONE_DIGIT -> R.string.one_digit
                    RequirementType.AT_LEAST_ONE_SPECIAL -> R.string.one_special_character
                    RequirementType.AT_LEAST_ONE_CAPITAL -> R.string.one_capital_letter
                    RequirementType.AT_LEAST_ONE_LOWERCASE -> R.string.one_lowercase_letter
                }
            ),
            style = MaterialTheme.typography.bodySmall
        )
    }
}