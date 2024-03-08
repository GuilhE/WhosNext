package com.whosnext.app.viewmodel

import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

/**
 * @param value in seconds
 * @param elapsed in seconds
 * @param progress from 0 to 100
 * @param backgroundIndex with background color index to choose from color list
 * @param isSettingTimer when true, should display timer's overlay
 * @param isCountingDown when true, disable timer's overlay
 * @param isRestarting when true, disable play, pause and restart buttons, and also timer's overlay
 */
@Serializable
data class TimerUiState(
    val value: Int = 0,
    val elapsed: Float = 0f,
    val progress: Float = 0f,
    val backgroundIndex: Int = 0,
    val isSettingTimer: Boolean = false,
    val isCountingDown: Boolean = false,
    val isRestarting: Boolean = false
) {
    fun settingTimer() = copy(isSettingTimer = true, isCountingDown = false, isRestarting = false)

    fun setTime(value: Int) = TimerUiState(value = value, progress = 0f, backgroundIndex = backgroundIndex)

    fun play() = copy(isSettingTimer = false, isCountingDown = true, isRestarting = false)

    fun pause() = copy(isSettingTimer = false, isCountingDown = false, isRestarting = false)

    fun stop() = copy(progress = 0f, elapsed = 0f, isSettingTimer = false, isCountingDown = false, isRestarting = false)

    fun restart(bgIndex: Int) =
        copy(progress = 0f, elapsed = 0f, backgroundIndex = bgIndex, isSettingTimer = false, isCountingDown = false, isRestarting = true)

    fun reset() = TimerUiState(backgroundIndex = backgroundIndex)

    fun isStopped(): Boolean = !isCountingDown && progress == 0f

    fun elapsedLabel(): String = timeToLabel((value - elapsed).roundToInt())
}