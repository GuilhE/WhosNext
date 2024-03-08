import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
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
        val wasmJsMain by getting
        wasmJsMain.dependencies {
            implementation(projects.shared)
            implementation(projects.sharedUi)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
    }
}

compose.experimental {
    web.application { }
}