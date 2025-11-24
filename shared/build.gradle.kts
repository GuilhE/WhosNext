plugins {
    id("buildlogic.plugins.kmp.library")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.nativecoroutines)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    android { namespace = "com.whosnext.shared" }
    jvm()
    wasmJs { browser() }
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework { baseName = "WhosNextShared" }
        iosTarget.compilerOptions { freeCompilerArgs.add("-Xexport-kdoc") }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.multiplatform.settings)
            api(libs.multiplatform.viewmodel)
            api(libs.koin.core)
            api(libs.kermit)
        }
        androidMain.dependencies { implementation(libs.koin.android) }
    }
}