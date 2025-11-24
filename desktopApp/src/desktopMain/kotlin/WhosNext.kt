import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import com.whosnext.app.DependencyInjection
import com.whosnext.app.ViewModels
import com.whosnext.ui.SplashScreenDesktop
import com.whosnext.ui.screens.TimerScreen

fun main() = application {
    val viewModel = remember {
        DependencyInjection.initKoin()
        ViewModels.timerViewModel().also { Logger.d("JVM\tobject ${System.identityHashCode(it)}") }
    }
    var showSplash by remember { mutableStateOf(true) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Who'sNext",
        resizable = false,
        state = WindowState(
            size = DpSize(400.dp, 800.dp),
            position = WindowPosition(Alignment.Center)
        ),
        alwaysOnTop = System.getProperty("compose.reload.isActive") == "true"
    ) {
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
                                SoundPlayer.playTimesUpSound()
                            }
                        } else {
                            notifying = false
                        }
                    }
                }
            }

            AnimatedVisibility(showSplash, exit = fadeOut()) {
                SplashScreenDesktop { showSplash = false }
            }
        }
    }
}