plugins {
    id("buildlogic.plugins.application")
}

android {
    namespace = "com.whosnext.app"
    defaultConfig {
        applicationId = "com.whosnext.app"
        versionCode = 1
        versionName = "1.0"

        resValue("string", "app_name_label", "WhosNext")
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
}

dependencies {
    implementation(projects.shared)
    implementation(projects.sharedUi)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.android.material)
    implementation(libs.koin.android)
}