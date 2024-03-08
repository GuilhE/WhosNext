package com.whosnext.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import whosnext.`shared-ui`.generated.resources.Res
import whosnext.`shared-ui`.generated.resources.helvetica_neue_light
import whosnext.`shared-ui`.generated.resources.whosnext_light_italic

@OptIn(ExperimentalResourceApi::class)
private val helveticaNeueFontFamily
    @Composable
    get() = FontFamily(Font(Res.font.helvetica_neue_light))

@OptIn(ExperimentalResourceApi::class)
private val whosNextFontFamily
    @Composable
    get() = FontFamily(Font(Res.font.whosnext_light_italic))

internal val labelTypography
    @Composable
    get() = Typography(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = helveticaNeueFontFamily),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = helveticaNeueFontFamily),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = helveticaNeueFontFamily),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = helveticaNeueFontFamily),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = helveticaNeueFontFamily),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = helveticaNeueFontFamily),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = helveticaNeueFontFamily),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = helveticaNeueFontFamily),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = helveticaNeueFontFamily),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = helveticaNeueFontFamily),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = helveticaNeueFontFamily),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = helveticaNeueFontFamily),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = helveticaNeueFontFamily),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = helveticaNeueFontFamily),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = helveticaNeueFontFamily),
    )

internal val digitsTypography
    @Composable
    get() = Typography(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = whosNextFontFamily),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = whosNextFontFamily),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = whosNextFontFamily),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = whosNextFontFamily),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = whosNextFontFamily),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = whosNextFontFamily),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = whosNextFontFamily),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = whosNextFontFamily),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = whosNextFontFamily),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = whosNextFontFamily),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = whosNextFontFamily),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = whosNextFontFamily),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = whosNextFontFamily),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = whosNextFontFamily),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = whosNextFontFamily),
    )