package com.whosnext.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.whosnext.ui.composables.Timer
import com.whosnext.ui.composables.TimerSetOverlay
import com.whosnext.ui.screens.colorFromTheme
import com.whosnext.ui.theme.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skiko.SkikoMouseButtons
import org.jetbrains.skiko.SkikoPointerEvent
import org.jetbrains.skiko.SkikoPointerEventKind
import whosnext.`shared-ui`.generated.resources.Res
import whosnext.`shared-ui`.generated.resources.bg_texture

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TimerScreenWasm(
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

    val pointerModifier = Modifier.pointerInput(isStopped) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val nativeEvent = event.nativeEvent
                if (nativeEvent is SkikoPointerEvent) {
                    if (nativeEvent.button == SkikoMouseButtons.RIGHT || nativeEvent.button == SkikoMouseButtons.LEFT) {
                        if (nativeEvent.kind == SkikoPointerEventKind.DOWN) {
                            if (isStopped) {
                                onSettingTimer.invoke()
                                lastDrag = 0f
                            }
                        } else if (nativeEvent.kind == SkikoPointerEventKind.UP) {
                            if (enableGestures) {
                                onTimeSet.invoke(minutesToSeconds(minutes) + seconds)
                            }
                        } else if (nativeEvent.kind == SkikoPointerEventKind.DRAG) {
                            val change = event.changes.first()
                            val amount = change.position - change.previousPosition
                            if (enableGestures) {
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
                    }
                }
            }
        }
    }

    val color by animateColorAsState(
        targetValue = colorFromTheme(colorIndex),
        animationSpec = tween(RESTART_ANIMATION_DURATION, easing = LinearOutSlowInEasing)
    )

    AppTheme {
        Box(modifier = Modifier.background(color)) {
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

            //TODO: Since we awaitPointerEventScope, use a different approach from detectDragGestures, when need to discard the
            // area we want to behave just like "click".
            // Remove this when pointerModifier for Web behaves like detectDragGestures for JVM/Native
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 150.dp)
                    .then(pointerModifier)
            ) { }
        }
    }
}
