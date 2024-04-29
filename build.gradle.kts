import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.android.tools)
        classpath(libs.gradle.kotlin)
        classpath(libs.gradle.kotlin.serialization)
    }
}

plugins {
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.jetbrainsComposeCompiler) apply false
}

allprojects {
    afterEvaluate {
        project.extensions.findByType<KotlinMultiplatformExtension>()?.let { ext ->
            ext.sourceSets {
                //https://discuss.kotlinlang.org/t/disabling-androidandroidtestrelease-source-set-in-gradle-kotlin-dsl-script
                sequenceOf("AndroidTest", "TestFixtures").forEach { artifact ->
                    sequenceOf("", "Release", "Debug").forEach { variant ->
                        findByName("android$artifact$variant")?.let(::remove)
                    }
                }
            }
        }
    }
}