package com.whosnext.ui.previews.composables

import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whosnext.ui.composables.CenterProgress
import com.whosnext.ui.theme.AppTheme
import com.whosnext.ui.theme.ColorPallet

@Preview
@Composable
private fun HeaderLogoPreview() {
    AppTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(ColorPallet.green)
        ) {
            CenterProgress(
                modifier = Modifier.fillMaxSize(),
                progress = 55f,
                elapsed = "20:24",
                isCountingDown = false,
                isRestarting = false,
                isSetting = false
            )
        }
    }
}