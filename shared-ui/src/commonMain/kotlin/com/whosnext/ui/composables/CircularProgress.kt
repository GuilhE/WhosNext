package com.whosnext.ui.composables

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun CircularProgress(
    modifier: Modifier = Modifier,
    color: Color,
    backgroundColor: Color = color,
    startingAngle: Float = 270f,
    progress: Float,
    animate: Boolean = true,
    animationSpec: ProgressAnimationSpec = ProgressAnimationSpec(250, LinearOutSlowInEasing)
) {
    val animatedProgress: Float by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = animationSpec.durationMillis, easing = animationSpec.easing)
    )
    Canvas(modifier) {
        val sweepAngle = (360 * if (animate) animatedProgress else progress) / 100
        val ringRadius = size.minDimension * 0.15f
        val size = Size(size.width, size.height)
        drawArc(backgroundColor, startingAngle, 360f, false, size = size, alpha = 0.2f, style = Stroke(ringRadius))
        drawArc(Color.Black, startingAngle, sweepAngle, false, Offset(0f, 15.dp.toPx()), size, 0.2f, Stroke(ringRadius, cap = StrokeCap.Round))
        drawArc(color, startingAngle, sweepAngle, false, size = size, style = Stroke(ringRadius, cap = StrokeCap.Round))
    }
}

internal data class ProgressAnimationSpec(val durationMillis: Int, val easing: Easing)