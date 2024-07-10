package com.whosnext.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import whosnext.shared_ui.generated.resources.Res
import whosnext.shared_ui.generated.resources.helvetica_neue_light
import whosnext.shared_ui.generated.resources.whosnext_light_italic

private val helveticaNeueFontFamily
    @Composable
    get() = FontFamily(Font(Res.font.helvetica_neue_light))

private val whosNextFontFamily
    @Composable
    get() = FontFamily(Font(Res.font.whosnext_light_italic))

internal val labelTypography: Typography
    @Composable
    get() {
        val ff = helveticaNeueFontFamily
        return Typography(
            displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = ff),
            displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = ff),
            displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = ff),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = ff),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = ff),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = ff),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = ff),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = ff),
            titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = ff),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = ff),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = ff),
            bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = ff),
            labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = ff),
            labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = ff),
            labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = ff),
        )
    }

internal val digitsTypography: Typography
    @Composable
    get() {
        val ff = whosNextFontFamily
        return Typography(
            displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = ff),
            displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = ff),
            displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = ff),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = ff),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = ff),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = ff),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = ff),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = ff),
            titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = ff),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = ff),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = ff),
            bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = ff),
            labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = ff),
            labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = ff),
            labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = ff),
        )
    }