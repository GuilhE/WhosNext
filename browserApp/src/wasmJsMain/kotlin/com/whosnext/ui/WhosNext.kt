package com.whosnext.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
import co.touchlab.kermit.Logger
import com.whosnext.app.DependencyInjection
import com.whosnext.app.ViewModels
import com.whosnext.ui.screens.TimerScreen

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "WhosNext") {
        val viewModel = remember {
            DependencyInjection.initKoin()
            ViewModels.timerViewModel().also { Logger.d("Wasm\tobject ${it.toJsReference()}") }
        }
        var showSplash by remember { mutableStateOf(true) }
        var showTimer by remember { mutableStateOf(false) }
        val minAvailableWidth = 400.dp
        val minAvailableHeight = 600.dp
        val windowSizeClass = calculateWindowSizeClass()
        val isCompactWindow = windowSizeClass.widthSizeClass.isCompact() || windowSizeClass.heightSizeClass.isCompact()

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val finalSize = DpSize(
                width = when {
                    windowSizeClass.widthSizeClass.isCompact() -> maxOf(maxWidth, minAvailableWidth)
                    else -> minAvailableWidth
                },
                height = when {
                    windowSizeClass.widthSizeClass.isCompact() -> maxOf(maxHeight, minAvailableHeight)
                    else -> minOf(800.dp, maxOf(minAvailableHeight, maxHeight))
                }
            )
            val size by animateSizeAsState(
                targetValue = if (showTimer) Size(finalSize.width.value, finalSize.height.value) else Size(maxWidth.value, maxHeight.value),
                animationSpec = spring(dampingRatio = 1.5f, stiffness = Spring.StiffnessMedium),
                finishedListener = {
                    if (showTimer) {
                        showSplash = false
                    }
                }
            )
            val corner by animateDpAsState(
                targetValue = if (!isCompactWindow && showTimer) 20.dp else 0.dp,
                animationSpec = spring(dampingRatio = 1.5f, stiffness = Spring.StiffnessMedium),
            )

            if (showTimer) {
                Box(
                    modifier = Modifier
                        .requiredSize(finalSize)
                        .align(Alignment.Center)
                        .graphicsLayer {
                            clip = true
                            shape = RoundedCornerShape(corner)
                        }
                ) {
                    with(viewModel.uiState.collectAsState().value) {
                        Box {
                            var notifying: Boolean by rememberSaveable { mutableStateOf(false) }
                            TimerScreen(
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
                    .size(size.width.dp, size.height.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(corner)
                    },
                exit = fadeOut()
            ) {
                SplashScreenWasm(minAvailableWidth, minAvailableHeight, onEnter = {
                    showTimer = true
                    if (isCompactWindow) {
                        showSplash = false
                    }
                })
            }
        }
    }
}

private fun WindowWidthSizeClass.isCompact() = this == WindowWidthSizeClass.Compact
private fun WindowHeightSizeClass.isCompact() = this == WindowHeightSizeClass.Compact