@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

plugins {
    id("buildlogic.plugins.kmp.library")
    id("buildlogic.plugins.kmp.compose")
    alias(libs.plugins.composeuiviewcontroller)
    alias(libs.plugins.stability.analyzer)
}

ComposeUiViewController {
    iosAppName = "WhosNext"
    targetName = "WhosNext"
}

kotlin {
    jvm("desktop")
    wasmJs { browser() }

    android { namespace = "com.whosnext.ui" }
    androidLibrary { androidResources.enable = true }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework { baseName = "WhosNextComposables" }
        target.compilerOptions { freeCompilerArgs.add("-Xbinary=bundleId=com.whosnext.ui") }
    }

    sourceSets {
        commonMain.dependencies { implementation(libs.bundles.jetbrains.compose) }
        androidMain.dependencies { implementation(libs.jetbrains.compose.ui.tooling) }
        iosMain.dependencies { implementation(projects.shared) }
    }
}