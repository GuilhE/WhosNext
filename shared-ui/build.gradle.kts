import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("buildlogic.plugins.kmp.library.android")
    id("org.jetbrains.compose")
}

compose {
    kotlinCompilerPlugin.set(libs.versions.composeMultiplatformCompiler)
}

android {
    namespace = "com.whosnext.ui"
}

kotlin {
    jvm("desktop")
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser() }
    listOf(iosArm64(), iosSimulatorArm64(), iosX64()).forEach { target ->
        target.binaries.framework { baseName = "WhosNextComposables" }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
            }
        }

        val desktopMain by getting
        desktopMain.dependencies { implementation(compose.preview) }

        iosMain {
            dependencies { implementation(projects.shared) }
            @Suppress("OPT_IN_USAGE")
            compilerOptions {
                freeCompilerArgs.add("-Xbinary=bundleId=com.whosnext.ui")
            }
        }
    }
}