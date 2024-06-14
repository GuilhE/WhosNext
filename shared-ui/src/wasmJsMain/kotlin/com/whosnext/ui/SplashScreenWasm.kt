package com.whosnext.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.whosnext.ui.screens.SplashScreen
import com.whosnext.ui.theme.SplashTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import whosnext.shared_ui.generated.resources.Res
import whosnext.shared_ui.generated.resources.bg_texture
import whosnext.shared_ui.generated.resources.lbl_start

@Composable
fun SplashScreenWasm(finalSize: DpSize, onStart: () -> Unit) {
    var showLabel by remember { mutableStateOf(true) }
    SplashTheme {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    showLabel = false
                    onStart()
                }
        ) {
            val height = remember { maxHeight }
            val scale by rememberInfiniteTransition().animateFloat(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.bg_texture),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            SplashScreen(
                modifier = Modifier
                    .align(Alignment.Center)
                    .requiredSize(DpSize(finalSize.width, height))
            )
            AnimatedVisibility(
                visible = showLabel,
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 350.dp)
            ) {
                Text(
                    modifier = Modifier.scale(scale),
                    text = stringResource(Res.string.lbl_start).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}