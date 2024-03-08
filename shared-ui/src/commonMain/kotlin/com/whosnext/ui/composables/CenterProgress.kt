@file:Suppress("unused", "SpellCheckingInspection")

package com.whosnext.ui.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.whosnext.ui.COUNTDOWN_STEP_ANIMATION_DURATION
import com.whosnext.ui.RESTART_ANIMATION_DURATION
import com.whosnext.ui.STOP_ANIMATION_DURATION
import com.whosnext.ui.theme.DigitsTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import whosnext.`shared-ui`.generated.resources.Res
import whosnext.`shared-ui`.generated.resources.bg_watch
import whosnext.`shared-ui`.generated.resources.ic_watch

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun CenterProgress(
    modifier: Modifier,
    progress: Float,
    elapsed: String,
    isCountingDown: Boolean,
    isRestarting: Boolean,
    isSetting: Boolean
) {
    val elapsedAlpha: Float by animateFloatAsState(if (isSetting) 0f else 1f)
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center)
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.requiredSize(250.dp),
                painter = painterResource(Res.drawable.bg_watch),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                painter = painterResource(Res.drawable.ic_watch),
                contentDescription = null
            )
            DigitsTheme {
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = elapsed,
                    style = MaterialTheme.typography.displayLarge,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = elapsedAlpha)
                )
            }

            CircularProgress(
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center),
                progress = progress,
                color = Color.White,
                backgroundColor = Color.Transparent,
                animationSpec = tween(
                    durationMillis = if (isRestarting) RESTART_ANIMATION_DURATION else (if (isCountingDown) COUNTDOWN_STEP_ANIMATION_DURATION else STOP_ANIMATION_DURATION),
                    easing = if (isCountingDown) LinearEasing else FastOutSlowInEasing
                )
            )
        }
    }
}