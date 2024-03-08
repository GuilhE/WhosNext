package com.whosnext.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.whosnext.ui.theme.SplashTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import whosnext.`shared-ui`.generated.resources.Res
import whosnext.`shared-ui`.generated.resources.bg_texture
import whosnext.`shared-ui`.generated.resources.ic_chrono_ball
import whosnext.`shared-ui`.generated.resources.ic_logo_alt
import whosnext.`shared-ui`.generated.resources.ic_zone

private val EaseOutBounce: Easing = Easing { fraction ->
    val n1 = 7.5625f
    val d1 = 2.75f
    var newFraction = fraction
    return@Easing if (newFraction < 1f / d1) {
        n1 * newFraction * newFraction
    } else if (newFraction < 2f / d1) {
        newFraction -= 1.5f / d1
        n1 * newFraction * newFraction + 0.75f
    } else if (newFraction < 2.5f / d1) {
        newFraction -= 2.25f / d1
        n1 * newFraction * newFraction + 0.9375f
    } else {
        newFraction -= 2.625f / d1
        n1 * newFraction * newFraction + 0.984375f
    }
}

private data class ChronoMetadata(val x: Float, val y: Float, val z: Float, val size: Float)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreenDesktop(modifier: Modifier = Modifier, onReady: () -> Unit) {
    var metadata by remember { mutableStateOf(ChronoMetadata(x = 45f, -38f, 0f, 70f)) }
    SplashTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.bg_texture),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(260.dp),
                    painter = painterResource(Res.drawable.ic_logo_alt),
                    contentDescription = null
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(metadata.size.dp)
                        .offset(x = metadata.x.dp, y = metadata.y.dp)
                        .rotate(metadata.z),
                    painter = painterResource(Res.drawable.ic_chrono_ball),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(Res.drawable.ic_zone),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .rotate(180f),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(Res.drawable.ic_zone),
                    modifier = Modifier.align(Alignment.BottomCenter),
                    contentDescription = null
                )

                LaunchedEffect(Unit) {
                    val animationY = Animatable(metadata.y)
                    val animationX = Animatable(metadata.x)
                    val animationZ = Animatable(metadata.z)
                    delay(2000)
                    launch {
                        animationY.animateTo(
                            targetValue = (maxHeight.value - (maxHeight.value / 2) - (metadata.size / 2)),
                            animationSpec = tween(durationMillis = 1000, easing = EaseOutBounce),
                            block = { metadata = metadata.copy(y = value) }
                        )
                    }
                    launch {
                        animationX.animateTo(
                            targetValue = metadata.x + 50.dp.value,
                            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
                            block = { metadata = metadata.copy(x = value) }
                        )
                    }
                    launch {
                        animationZ.animateTo(
                            targetValue = metadata.z + 45,
                            animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing),
                            block = { metadata = metadata.copy(z = value) }
                        )
                    }
                    delay(2000)
                    onReady()
                }
            }
        }
    }
}