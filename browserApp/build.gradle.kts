plugins {
    id("buildlogic.plugins.kmp.compose")
}

@OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl::class)
kotlin {
    wasmJs {
        moduleName = "browserApp"
        browser {
            commonWebpackConfig {
                outputFileName = "browserApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(projects.shared)
                implementation(projects.sharedUi)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(libs.kmp.windowsizeclass)
            }
        }
    }
}