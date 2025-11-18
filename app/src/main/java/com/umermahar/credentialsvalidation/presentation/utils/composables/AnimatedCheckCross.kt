package com.umermahar.credentialsvalidation.presentation.utils.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedCheckCross(
    isChecked: Boolean,
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    val successColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
    val errorColor = MaterialTheme.colorScheme.error.copy(alpha = 0.9f)

    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val color = lerp(errorColor, successColor, progress) // blend between red ↔ green

        val path = Path()

        // --- Cross lines parameters ---
        val crossLength = size.width * 0.6f
        val start1 = Offset(size.width * 0.2f, size.height * 0.2f)
        val end1 = Offset(start1.x + crossLength, start1.y + crossLength)
        val start2 = Offset(size.width * 0.8f, size.height * 0.2f)
        val end2 = Offset(start2.x - crossLength, start2.y + crossLength)

        // --- Check mark parameters ---
        val checkStart = Offset(size.width * 0.2f, size.height * 0.55f)
        val checkMid = Offset(size.width * 0.40f, size.height * 0.75f)
        val checkEnd = Offset(size.width * 0.8f, size.height * 0.3f)

        // Animate between shapes
        if (progress < 0.5f) {
            // progress: 0..0.5 -> visibleFraction: 1..0
            val visibleFraction = 1f - (progress / 0.5f)

            // centers of each diagonal line
            val center1 = Offset((start1.x + end1.x) / 2f, (start1.y + end1.y) / 2f)
            val center2 = Offset((start2.x + end2.x) / 2f, (start2.y + end2.y) / 2f)

            fun interpolator(from: Offset, to: Offset, f: Float) =
                Offset(from.x + (to.x - from.x) * f, from.y + (to.y - from.y) * f)

            // Interpolate endpoints toward/away from center based on visibleFraction
            val s1 = interpolator(center1, start1, visibleFraction)
            val e1 = interpolator(center1, end1, visibleFraction)
            val s2 = interpolator(center2, start2, visibleFraction)
            val e2 = interpolator(center2, end2, visibleFraction)

            // Draw the two lines with rounded caps
            drawLine(
                color = color,
                start = s1,
                end = e1,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = color,
                start = s2,
                end = e2,
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        } else {
            // Draw check fading in
            val checkProgress = (progress - 0.5f) / 0.5f

            path.moveTo(checkStart.x, checkStart.y)
            val midProgress = checkProgress.coerceAtMost(0.5f) * 2f
            path.lineTo(
                checkStart.x + (checkMid.x - checkStart.x) * midProgress,
                checkStart.y + (checkMid.y - checkStart.y) * midProgress
            )
            if (checkProgress > 0.5f) {
                val endProgress = (checkProgress - 0.5f) * 2f
                path.lineTo(
                    checkMid.x + (checkEnd.x - checkMid.x) * endProgress,
                    checkMid.y + (checkEnd.y - checkMid.y) * endProgress
                )
            }

            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        }
    }
}