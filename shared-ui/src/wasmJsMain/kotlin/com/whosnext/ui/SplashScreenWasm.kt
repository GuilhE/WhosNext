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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whosnext.ui.screens.SplashScreen
import com.whosnext.ui.theme.SplashTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import whosnext.shared_ui.generated.resources.Res
import whosnext.shared_ui.generated.resources.bg_texture
import whosnext.shared_ui.generated.resources.lbl_start

@Composable
fun SplashScreenWasm(minWidth: Dp, minHeight: Dp, onEnter: () -> Unit) {
    var showLabel by remember { mutableStateOf(true) }
    SplashTheme {
        Box(
            Modifier
                .requiredSizeIn(minWidth, minHeight)
                .background(MaterialTheme.colorScheme.background)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    enabled = showLabel
                ) {
                    showLabel = false
                    onEnter()
                }
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.bg_texture),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Box(contentAlignment = Alignment.Center) {
                SplashScreen(modifier = Modifier.fillMaxSize())
                AnimatedVisibility(
                    modifier = Modifier.padding(top = 170.dp),
                    visible = showLabel,
                    exit = fadeOut()
                ) {
                    val scale by rememberInfiniteTransition().animateFloat(
                        initialValue = 1f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                    Text(
                        modifier = Modifier
                            .scale(scale)
                            .padding(top = 50.dp),
                        text = stringResource(Res.string.lbl_start).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}