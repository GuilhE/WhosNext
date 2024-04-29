package com.whosnext.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.whosnext.ui.DRAG_STEP_LAG
import com.whosnext.ui.RESTART_ANIMATION_DURATION
import com.whosnext.ui.composables.Timer
import com.whosnext.ui.composables.TimerSetOverlay
import com.whosnext.ui.minutesInTime
import com.whosnext.ui.minutesToSeconds
import com.whosnext.ui.secondsInTime
import com.whosnext.ui.theme.AppTheme
import com.whosnext.ui.theme.BlueTheme
import com.whosnext.ui.theme.GreenTheme
import com.whosnext.ui.theme.OrangeTheme
import com.whosnext.ui.theme.PinkTheme
import com.whosnext.ui.theme.PurpleTheme
import com.whosnext.ui.theme.YellowTheme
import org.jetbrains.compose.resources.painterResource
import whosnext.shared_ui.generated.resources.Res
import whosnext.shared_ui.generated.resources.bg_texture

@Composable
fun TimerScreen(
    progress: Float,
    elapsed: String,
    value: Int,
    colorIndex: Int,
    isSettingTimer: Boolean,
    isCountingDown: Boolean,
    isRestarting: Boolean,
    isStopped: Boolean,
    onSettingTimer: () -> Unit,
    onTimeSet: (Int) -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit
) {
    var isDraggingLeft: Boolean by remember { mutableStateOf(false) }
    var lastDrag: Float by remember { mutableStateOf(0f) }
    var minutes: Int by remember { mutableStateOf(0) }
    var seconds: Int by remember { mutableStateOf(0) }
    minutes = minutesInTime(value)
    seconds = secondsInTime(value)

    var enableGestures: Boolean by remember { mutableStateOf(false) }
    enableGestures = isSettingTimer

    val modifier = Modifier.pointerInput(isStopped) {
        detectDragGestures(
            onDragStart = {
                if (isStopped) {
                    onSettingTimer.invoke()
                    lastDrag = 0f
                }
            },
            onDragEnd = {
                if (enableGestures) {
                    onTimeSet.invoke(minutesToSeconds(minutes) + seconds)
                }
            },
            onDrag = { change, amount ->
                if (enableGestures) {
                    change.consume()
                    val left = change.position.x < size.width / 2
                    if (lastDrag > DRAG_STEP_LAG) {
                        lastDrag = 0f
                        val res = if (amount.y > 0) -1 else 1
                        if (left) {
                            minutes += if (minutes + res in 0..59) res else 0
                        } else {
                            seconds += if (seconds + res in 0..59) res else 0
                        }
                    } else {
                        //to "reduce" drag velocity and reset when changing sides
                        val verticalDrag = if (isDraggingLeft == left) amount.y.toInt() else 0
                        lastDrag += kotlin.math.abs(verticalDrag)
                    }
                    isDraggingLeft = left
                }
            }
        )
    }

    val color by animateColorAsState(
        targetValue = colorFromTheme(colorIndex),
        animationSpec = tween(RESTART_ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

    AppTheme {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.bg_texture),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )

            Timer(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                elapsed = elapsed,
                value = value,
                isCountingDown = isCountingDown,
                isRestarting = isRestarting,
                isStopped = isStopped,
                isSetting = isSettingTimer,
                onStart = onStart,
                onPause = onPause,
                onStop = onStop,
                onReset = onReset
            )

            AnimatedVisibility(
                visibleState = remember { MutableTransitionState(false) }.apply { targetState = isSettingTimer },
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                TimerSetOverlay(
                    modifier = Modifier.fillMaxSize(),
                    minutes = minutes,
                    seconds = seconds,
                    isDraggingLeft = isDraggingLeft
                )
            }
        }
    }
}

@Composable
internal fun colorFromTheme(colorIndex: Int): Color {
    var color = Color.Unspecified
    when (colorIndex) {
        1 -> PinkTheme { color = MaterialTheme.colorScheme.background }
        2 -> OrangeTheme { color = MaterialTheme.colorScheme.background }
        3 -> PurpleTheme { color = MaterialTheme.colorScheme.background }
        4 -> BlueTheme { color = MaterialTheme.colorScheme.background }
        5 -> YellowTheme { color = MaterialTheme.colorScheme.background }
        else -> GreenTheme { color = MaterialTheme.colorScheme.background }
    }
    return color
}