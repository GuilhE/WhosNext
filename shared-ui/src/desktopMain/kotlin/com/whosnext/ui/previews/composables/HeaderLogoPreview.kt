package com.whosnext.ui.previews.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whosnext.ui.composables.HeaderLogo
import com.whosnext.ui.theme.ColorPallet

@Preview
@Composable
private fun HeaderLogoPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPallet.green)
    ) {
        HeaderLogo(modifier = Modifier.fillMaxSize(), isRestarting = false)
    }
}