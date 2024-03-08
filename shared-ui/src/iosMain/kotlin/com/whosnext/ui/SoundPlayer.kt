package com.whosnext.ui

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSBundle

@OptIn(ExperimentalForeignApi::class)
internal object SoundPlayer {
    private val audioPlayer: AVAudioPlayer?
    private val soundUrl = NSBundle.mainBundle.URLForResource("timesup", "mp3")

    init {
        audioPlayer = try {
            AVAudioPlayer(soundUrl!!, null)
        } catch (e: Exception) {
            println("Error creating AVAudioPlayer")
            null
        }
        audioPlayer?.prepareToPlay()
    }

    fun playTimesUpSound() {
        soundUrl?.let { audioPlayer?.play() }
    }
}

