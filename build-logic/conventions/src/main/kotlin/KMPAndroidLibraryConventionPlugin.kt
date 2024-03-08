@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.gradle.LibraryExtension
import extensions.addKotlinAndroidConfigurations
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.library")
            extensions.configure<LibraryExtension> {
                addKotlinAndroidConfigurations(extensions.getByType<VersionCatalogsExtension>().named("libs")).also {
                    sourceSets.getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
                }
            }
            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = JavaVersion.VERSION_17.toString()
                        }
                    }
                }
            }
        }
    }
}