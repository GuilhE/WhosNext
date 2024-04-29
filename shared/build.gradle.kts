plugins {
    id("buildlogic.plugins.kmp.library.android")
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
    alias(libs.plugins.nativecoroutines)
}

android {
    namespace = "com.whosnext.shared"
}

@OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl::class)
kotlin {
    androidTarget()
    jvm()
    wasmJs { browser() }
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "WhosNextShared"
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
            implementation(libs.jetbrains.kotlinx.coroutines.core)
            implementation(libs.jetbrains.kotlinx.serialization)
            implementation(libs.jetbrains.kotlinx.atomicfu)
            implementation(libs.kmp.settings)
            api(libs.kmp.viewmodel)
            api(libs.koin.core)
            api(libs.kermit)
        }
        androidMain.dependencies { implementation(libs.koin.android) }
        iosMain {
            @Suppress("OPT_IN_USAGE")
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }
}