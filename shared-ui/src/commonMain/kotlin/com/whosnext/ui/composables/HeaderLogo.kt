@file:Suppress("unused", "SpellCheckingInspection")

package com.whosnext.ui.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import whosnext.shared_ui.generated.resources.Res
import whosnext.shared_ui.generated.resources.ic_chrono_ball
import whosnext.shared_ui.generated.resources.ic_logo_alt

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun HeaderLogo(
    modifier: Modifier,
    isRestarting: Boolean,
) {
    val chronoBallAngle: Float by rememberInfiniteTransition().animateFloat(
        -15f, 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 50, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp),
            painter = painterResource(Res.drawable.ic_logo_alt),
            contentDescription = null
        )
        Box(
            modifier = Modifier.align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .offset(x = 30.dp, y = 32.dp)
                    .rotate(if (isRestarting) chronoBallAngle else 0f),
                painter = painterResource(Res.drawable.ic_chrono_ball),
                contentDescription = null
            )
        }
    }
}