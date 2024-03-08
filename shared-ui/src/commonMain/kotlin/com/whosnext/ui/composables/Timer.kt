@file:Suppress("unused", "SpellCheckingInspection")

package com.whosnext.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Timer(
    modifier: Modifier,
    progress: Float,
    elapsed: String,
    value: Int,
    isCountingDown: Boolean,
    isRestarting: Boolean,
    isStopped: Boolean,
    isSetting: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        HeaderLogo(
            modifier = Modifier.fillMaxWidth(),
            isRestarting = isRestarting
        )
        CenterProgress(
            modifier = Modifier.fillMaxWidth(),
            progress = progress,
            elapsed = elapsed,
            isCountingDown = isCountingDown,
            isRestarting = isRestarting,
            isSetting = isSetting,
        )
        BottomButtons(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            isCountingDown = isCountingDown,
            isRestarting = isRestarting,
            isStopped = isStopped,
            onStart = onStart,
            onPause = onPause,
            onReset = onReset,
            onStop = onStop
        )
    }
}