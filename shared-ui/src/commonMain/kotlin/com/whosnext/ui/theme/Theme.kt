package com.whosnext.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme =
        if (darkTheme) MaterialTheme.colorScheme.copy(
            primary = ColorPallet.red_dark,
            onPrimary = Color.White,
            onBackground = Color.White
        ) else MaterialTheme.colorScheme.copy(
            primary = ColorPallet.red,
            background = ColorPallet.white_ghost,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun SplashTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.green_alt,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun OverlayTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = Color.Black,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun DigitsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = Color.Black,
            onBackground = Color.White
        ),
        typography = digitsTypography,
        content = content
    )
}

@Composable
internal fun GreenTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.green,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun OrangeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.orange,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun PurpleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.purple,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun BlueTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.blue,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun YellowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.yellow,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}

@Composable
internal fun PinkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = ColorPallet.pink,
            onBackground = Color.White
        ),
        typography = labelTypography,
        content = content
    )
}