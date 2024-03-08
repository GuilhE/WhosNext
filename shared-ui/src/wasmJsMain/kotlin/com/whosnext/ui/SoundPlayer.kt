package com.whosnext.ui

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

internal object SoundPlayer {
    private val audioElement = document.createElement("audio") as HTMLAudioElement

    init {
        audioElement.addEventListener("canplaythrough") { }
        audioElement.src = "timesup.wav"
    }

    fun playTimesUpSound() {
        audioElement.play()
    }
}