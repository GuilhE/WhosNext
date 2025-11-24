plugins {
    id("buildlogic.plugins.application")
}

android {
    namespace = "com.whosnext.app"
    defaultConfig {
        applicationId = "com.whosnext.app"
        versionCode = 6
        versionName = "2.0.0"

        resValue("string", "app_name_label", "WhosNext")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.sharedUi)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.compose.activity)
    implementation(libs.bundles.jetbrains.compose)
    implementation(libs.android.material)
    implementation(libs.koin.android)
}