package com.whosnext.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.github.guilhe.kmp.composeuiviewcontroller.ComposeUIViewController
import com.whosnext.app.DependencyInjection
import com.whosnext.app.ViewModels
import com.whosnext.ui.screens.TimerScreen
import org.koin.core.error.KoinApplicationAlreadyStartedException

@ComposeUIViewController
@Composable
internal fun TimerScreenInCompose() {
    val viewModel = remember {
        try {
            DependencyInjection.initKoin()
        } catch (ignore: KoinApplicationAlreadyStartedException) {
        }
        ViewModels.timerViewModel()
    }

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