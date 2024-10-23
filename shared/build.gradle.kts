import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("buildlogic.plugins.kmp.library.android")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.nativecoroutines)
    alias(libs.plugins.kotlinx.atomicfu)
}

android {
    namespace = "com.whosnext.shared"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    androidTarget()
    jvm()
    wasmJs { browser() }
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework { baseName = "WhosNextShared" }
        compilerOptions {
            freeCompilerArgs.add("-Xexport-kdoc")
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlin.experimental.ExperimentalObjCName")
                optIn("kotlin.RequiresOptIn")
            }
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kmp.settings)
            api(libs.kmp.viewmodel)
            api(libs.kmp.koin.core)
            api(libs.kmp.kermit)
            //https://github.com/Kotlin/kotlinx-atomicfu/issues/469#issuecomment-2326868412
            implementation("org.jetbrains.kotlinx:atomicfu:0.26.0")
        }
        androidMain.dependencies { implementation(libs.kmp.koin.android) }
        wasmJsMain.dependencies { implementation(libs.kotlinx.browser) }
    }
}