package com.whosnext.ui.previews.screens

import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whosnext.ui.composables.TimerSetOverlay
import com.whosnext.ui.screens.TimerScreen
import com.whosnext.ui.screens.colorFromTheme

@Composable
@Preview
private fun TimerPreview() {
    TimerScreen(60f, "20:24", 0, 0, isSettingTimer = false, isCountingDown = false, isRestarting = false, isStopped = false, {}, {}, {}, {}, {}, {})
}

@Composable
@Preview
private fun TimerSetOverlayPreview() {
    TimerSetOverlay(Modifier.fillMaxSize(), 20, 24, true)
}

@Composable
@Preview
private fun TimerWithOverlayPreview() {
    Box {
        TimerScreen(
            60f,
            "20:24",
            0,
            0,
            isSettingTimer = false,
            isCountingDown = false,
            isRestarting = false,
            isStopped = false,
            {},
            {},
            {},
            {},
            {},
            {})
        TimerSetOverlay(Modifier.fillMaxSize(), 20, 24, true)
    }
}

@Composable
@Preview
private fun ColorFromThemePreview() {
    Row {
        Box(
            Modifier
                .size(50.dp)
                .background(colorFromTheme(1))
        )
        Box(
            Modifier
                .size(50.dp)
                .background(colorFromTheme(2))
        )
        Box(
            Modifier
                .size(50.dp)
                .background(colorFromTheme(3))
        )
        Box(
            Modifier
                .size(50.dp)
                .background(colorFromTheme(4))
        )
        Box(
            Modifier
                .size(50.dp)
                .background(colorFromTheme(5))
        )
        Box(
            Modifier
                .size(50.dp)
                .background(colorFromTheme(6))
        )
    }
}