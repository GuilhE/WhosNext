plugins {
    id("buildlogic.plugins.kmp.library")
    id("buildlogic.plugins.kmp.compose")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.composeuiviewcontroller)
    alias(libs.plugins.stability.analyzer)
}

ComposeUiViewController {
    iosAppName = "WhosNext"
    targetName = "WhosNext"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    android { namespace = "com.whosnext.ui" }
    jvm("desktop")
    wasmJs { browser() }
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework { baseName = "WhosNextComposables" }
        target.compilerOptions { freeCompilerArgs.add("-Xbinary=bundleId=com.whosnext.ui") }
    }

    sourceSets {
        commonMain.dependencies { implementation(libs.bundles.jetbrains.compose) }
        iosMain.dependencies { implementation(projects.shared) }
    }
}