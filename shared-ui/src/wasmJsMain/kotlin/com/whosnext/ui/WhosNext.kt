package com.whosnext.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.whosnext.app.DependencyInjection
import com.whosnext.app.ViewModels

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "WhosNext") {
        val viewModel = remember {
            DependencyInjection.initKoin()
            ViewModels.timerViewModel()
        }
        var showSplash by remember { mutableStateOf(true) }
        var showTimer by remember { mutableStateOf(false) }
        val finalSize = DpSize(400.dp, 800.dp)

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val fullWith = remember { maxWidth }
            val fullHeight = remember { maxHeight }
            val size by animateSizeAsState(
                targetValue = if (showTimer) Size(finalSize.width.value, finalSize.height.value) else Size(fullWith.value, fullHeight.value),
                animationSpec = spring(dampingRatio = 1.5f, stiffness = Spring.StiffnessMedium),
                finishedListener = { showSplash = false }
            )

            if (showTimer) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer {
                            clip = true
                            shape = RoundedCornerShape(20.dp)
                        }
                        .requiredSize(finalSize)
                ) {
                    with(viewModel.uiState.collectAsState().value) {
                        Box {
                            var notifying: Boolean by rememberSaveable { mutableStateOf(false) }
                            TimerScreenWasm(
                                progress = progress,
                                elapsed = elapsedLabel(),
                                value = value,
                                colorIndex = backgroundIndex,
                                isSettingTimer = isSettingTimer,
                                isCountingDown = isCountingDown,
                                isRestarting = isRestarting,
                                isStopped = isStopped(),
                                onSettingTimer = { viewModel.settingTime() },
                                onTimeSet = { viewModel.setTime(it) },
                                onStart = { viewModel.start() },
                                onPause = { viewModel.pause() },
                                onStop = { viewModel.stop() },
                                onReset = { viewModel.reset() }
                            )

                            if (isRestarting) {
                                if (!notifying) {
                                    notifying = true
                                    SoundPlayer.playTimesUpSound()
                                }
                            } else {
                                notifying = false
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = showSplash,
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(if (showTimer) 20.dp else 0.dp)
                    }
                    .requiredSize(DpSize(size.width.dp, size.height.dp)),
                exit = fadeOut()
            ) {
                SplashScreenWasm {
                    showTimer = true
                }
            }
        }
    }
}