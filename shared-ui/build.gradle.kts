import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
//    id("buildlogic.plugins.kmp.library.android")  //TODO: uncomment when compose.components.resources support multi module
    id("org.jetbrains.compose")
    //TODO: remove the following when compose.components.resources support multi module
    kotlin("multiplatform")
    id("com.android.application")
}

compose {
    kotlinCompilerPlugin.set(libs.versions.composeMultiplatformCompiler)
    applyTemporaryWasmSettings()    //TODO: remove when compose.components.resources support multi module
}

android {
    namespace = "com.whosnext.ui"
    applyTemporaryAndroidSettings()    //TODO: remove when compose.components.resources support multi module
}

kotlin {
    androidTarget() {
        //TODO: remove this configurations when buildlogic.plugins.kmp.library.android could be imported
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
    jvm("desktop")
    applyTemporaryWasmSettings()    //TODO: remove when compose.components.resources support multi module
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

        iosMain.dependencies { implementation(projects.shared) }
        targets.withType<KotlinNativeTarget> {
            compilations["main"].kotlinOptions.freeCompilerArgs += "-Xbinary=bundleId=com.whosnext.ui"
        }
    }
}

//TODO:
//  Multi module projects are not fully supported yet when using compose.components.resources.
//  https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-images-resources.html
//  When they do, we can remove the following functions and use the [browserApp] and [androidApp] module

fun BaseAppModuleExtension.applyTemporaryAndroidSettings() {
    namespace = "com.whosnext.app"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.whosnext.app"
        versionCode = 5
        versionName = "2.0.0"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        resValue("string", "app_name_label", "WhosNext")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        disable.add("Instantiatable")
        abortOnError = false
    }

    packaging {
        // Optimize APK size - remove excess files in the manifest and APK
        resources {
            excludes.addAll(
                listOf(
                    "**/kotlin/**",
                    "**/*.kotlin_module",
                    "**/*.version",
                    "**/*.txt",
                    "**/*.properties",
                    "/META-INF/{AL2.0,LGPL2.1}"
                )
            )
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    dependencies {
        implementation(projects.shared)
        implementation(libs.koin.android)
        implementation(libs.android.material)
        implementation(libs.androidx.compose.activity)
        implementation(libs.androidx.compose.ui.tooling.preview)
    }
}

fun KotlinMultiplatformExtension.applyTemporaryWasmSettings() {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "browserApp"
        browser {
            commonWebpackConfig {
                outputFileName = "browserApp.js"
            }
        }
        binaries.executable()
        sourceSets {
            val wasmJsMain by getting {
                dependencies {
                    implementation(projects.shared)
                }
            }
        }
    }
}

fun ComposeExtension.applyTemporaryWasmSettings() {
    experimental {
        web.application { }
    }
}