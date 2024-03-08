package com.whosnext.app

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual class PlatformDependencies actual constructor() {
    actual fun getSettings(): Settings = StorageSettings()
}