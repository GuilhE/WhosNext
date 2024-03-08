package com.whosnext.app

import com.russhwolf.settings.Settings

expect class PlatformDependencies() {
    fun getSettings(): Settings
}