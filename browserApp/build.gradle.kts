import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("buildlogic.plugins.kmp.compose")
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "browserApp"
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
                implementation(libs.bundles.jetbrains.compose)
            }
        }
    }
}