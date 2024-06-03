plugins {
    id("buildlogic.plugins.kmp.library.android")
    id("buildlogic.plugins.kmp.compose")
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "com.whosnext.ui"
}

@OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl::class)
kotlin {
    jvm("desktop")
    wasmJs { browser() }
    listOf(iosArm64(), iosSimulatorArm64(), iosX64()).forEach { target ->
        target.binaries.framework { baseName = "WhosNextComposables" }
        val targetName = target.name.replaceFirstChar { it.uppercaseChar() }
        dependencies.add("ksp$targetName", libs.kmp.composeuiviewcontroller.ksp)
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
        val desktopMain by getting {
            dependencies { implementation(compose.preview) }
        }
        iosMain {
            dependencies {
                implementation(projects.shared)
                implementation(libs.kmp.composeuiviewcontroller.annotations)
            }
            @Suppress("OPT_IN_USAGE")
            compilerOptions {
                freeCompilerArgs.add("-Xbinary=bundleId=com.whosnext.ui")
            }
        }
    }
}

tasks.matching { it.name == "embedAndSignAppleFrameworkForXcode" }.configureEach { finalizedBy(":addFilesToXcodeproj") }