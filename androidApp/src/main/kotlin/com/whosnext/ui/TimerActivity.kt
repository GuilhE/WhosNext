package com.whosnext.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.whosnext.app.R
import com.whosnext.app.viewmodel.TimerViewModel
import com.whosnext.ui.screens.SplashScreen
import com.whosnext.ui.screens.TimerScreen
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TimerActivity : ComponentActivity() {

    private val timesUp: MediaPlayer by lazy { MediaPlayer.create(this, R.raw.timesup) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val viewModel = getViewModel<TimerViewModel>()

        setContent {
            var showSplash by remember { mutableStateOf(true) }
            Box {
                if (!showSplash) {
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
                                    timesUp.apply {
                                        stop()
                                        prepare()
                                    }
                                    timesUp.start()
                                }
                            } else {
                                notifying = false
                            }
                        }
                    }
                }

                AnimatedVisibility(showSplash, exit = fadeOut()) {
                    SplashScreen()
                }
            }

            LaunchedEffect(Unit) {
                delay(2000)
                showSplash = false
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SplashScreen()
}