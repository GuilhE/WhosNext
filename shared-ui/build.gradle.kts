plugins {
    id("buildlogic.plugins.kmp.library.android")
    id("buildlogic.plugins.kmp.compose")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.composeuiviewcontroller)
}

ComposeUiViewController {
    iosAppName="WhosNext"
    targetName="WhosNext"
}

android {
    namespace = "com.whosnext.ui"
    dependencies { debugImplementation(compose.uiTooling) }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    jvm("desktop")
    wasmJs { browser() }
    listOf(iosArm64(), iosSimulatorArm64(), iosX64()).forEach { target ->
        target.binaries.framework { baseName = "WhosNextComposables" }
        compilerOptions {
            freeCompilerArgs.add("-Xbinary=bundleId=com.whosnext.ui")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        iosMain {
            dependencies {
                implementation(projects.shared)
            }
        }
    }
}