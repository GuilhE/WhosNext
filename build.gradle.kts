import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.android.tools)
        classpath(libs.gradle.kotlin)
        classpath(libs.gradle.kotlin.serialization)
        classpath(libs.gradle.compose.multiplatform)
    }
}

allprojects {
    afterEvaluate {
        //https://discuss.kotlinlang.org/t/disabling-androidandroidtestrelease-source-set-in-gradle-kotlin-dsl-script
        project.extensions.findByType<KotlinMultiplatformExtension>()?.let { ext ->
            ext.sourceSets {
                sequenceOf("AndroidTest", "TestFixtures").forEach { artifact ->
                    sequenceOf("", "Release", "Debug").forEach { variant ->
                        findByName("android$artifact$variant")?.let(::remove)
                    }
                }
            }
        }
    }
}

// TODO: Remove once default NodeJS version supports wasm
rootProject.extensions.findByType<NodeJsRootExtension>()?.apply {
    nodeVersion = "v22.0.0-v8-canary20240219209428711c"
    nodeDownloadBaseUrl = "https://nodejs.org/download/v8-canary"
}
tasks.withType<KotlinNpmInstallTask>().configureEach {
    args.add("--ignore-engines")
}