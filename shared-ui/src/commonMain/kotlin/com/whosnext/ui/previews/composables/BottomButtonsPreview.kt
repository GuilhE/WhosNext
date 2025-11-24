package com.whosnext.ui.previews.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whosnext.ui.composables.BottomButtons
import com.whosnext.ui.theme.ColorPallet

@Preview
@Composable
private fun BottomButtonsPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(ColorPallet.green),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        BottomButtons(modifier = Modifier.fillMaxWidth(),
            60,
            isCountingDown = false,
            isRestarting = false,
            isStopped = true,
            onStart = { },
            onPause = { },
            onReset = { },
            onStop = {})
        BottomButtons(modifier = Modifier.fillMaxWidth(),
            60,
            isCountingDown = true,
            isRestarting = false,
            isStopped = false,
            onStart = { },
            onPause = { },
            onReset = { },
            onStop = {})
        BottomButtons(modifier = Modifier.fillMaxWidth(),
            60,
            isCountingDown = false,
            isRestarting = true,
            isStopped = false,
            onStart = { },
            onPause = { },
            onReset = { },
            onStop = {})
    }
}