@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.api.dsl.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        pluginManager.apply("com.android.kotlin.multiplatform.library")

        val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
        extensions.configure<KotlinMultiplatformExtension> {
            androidLibrary {
                compileSdk = catalog.findVersion("androidCompileSdk").get().toString().toInt()
                minSdk = catalog.findVersion("androidMinSdk").get().toString().toInt()
                compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
                lint {
                    disable.add("Instantiatable")
                    abortOnError = false
                }
            }
            sourceSets.all {
                languageSettings.apply {
                    optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                    optIn("kotlin.experimental.ExperimentalObjCName")
                    optIn("kotlin.RequiresOptIn")
                }
            }
        }
    }
}