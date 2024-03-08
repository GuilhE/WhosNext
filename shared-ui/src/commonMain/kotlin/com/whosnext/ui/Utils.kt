package com.whosnext.ui

private const val STEP_DELAY: Long = 150L
private const val RESTART_DELAY: Long = 3000L
internal const val DRAG_STEP_LAG = 10
const val COUNTDOWN_STEP_ANIMATION_DURATION: Int = STEP_DELAY.toInt()
const val RESTART_ANIMATION_DURATION: Int = RESTART_DELAY.toInt() - 1000
const val STOP_ANIMATION_DURATION: Int = 300

internal fun secondsInTime(time: Int) = time % 60

internal fun minutesInTime(time: Int) = (time % 3600) / 60

fun minutesToSeconds(time: Int) = time * 60

fun padTime(time: Int): String = if (time < 10) "$time".padStart(2, '0') else "$time"