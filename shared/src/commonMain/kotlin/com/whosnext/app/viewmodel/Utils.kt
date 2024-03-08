package com.whosnext.app.viewmodel

internal fun normalize(value: Float, fromMin: Float, fromMax: Float, toMin: Float, toMax: Float): Float {
    // [min,max] to [a,b] >>> f(x) = (b - a) (x - min) / (max - min) + a
    val v = when {
        value > fromMax -> fromMax
        value < fromMin -> fromMin
        else -> value
    }
    return (toMax - toMin) * (v - fromMin) / (fromMax - fromMin) + toMin
}

internal fun timeToLabel(time: Int): String {
    return if (time == 0) "00:00" else {
        val min = minutesInTime(time)
        val sec = secondsInTime(time)
        "${padTime(min)}:${padTime(sec)}"
    }
}

private fun secondsInTime(time: Int) = time % 60

private fun minutesInTime(time: Int) = (time % 3600) / 60

private fun padTime(time: Int): String = if (time < 10) "$time".padStart(2, '0') else "$time"