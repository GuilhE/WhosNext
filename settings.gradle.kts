@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
        maven("https://redirector.kotlinlang.org/maven/dev")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev/")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    //https://youtrack.jetbrains.com/issue/KT-68533/
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://redirector.kotlinlang.org/maven/dev")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev/")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "WhosNext"
include(":androidApp")
include(":browserApp")
include(":desktopApp")
include(":shared")
include(":shared-ui")