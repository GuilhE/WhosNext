package com.whosnext.app.fsm

import com.whosnext.app.viewmodel.timeToLabel
import kotlinx.serialization.Serializable

internal sealed class TimerState {
    @Serializable
    sealed class State {
        @Serializable
        data object Idle : State()

        @Serializable
        data object SettingTimer : State()

        @Serializable
        data object CountingDown : State()

        @Serializable
        data object Paused : State()

        @Serializable
        data object Restarting : State()
    }

    @Serializable
    sealed class Event {
        @Serializable
        data object OnSetTimer : Event()

        @Serializable
        class OnTimerSet(val value: Int) : Event() {
            override fun toString(): String = "OnTimerSet ${timeToLabel(value)}"
        }

        @Serializable
        data object OnStart : Event()

        @Serializable
        data object OnFinish : Event()

        @Serializable
        data object OnPause : Event()

        @Serializable
        data object OnStop : Event()

        @Serializable
        data object OnReset : Event()
    }

    sealed class SideEffect {
        data object SetTimer : SideEffect()

        class TimerReady(val value: Int) : SideEffect() {
            override fun toString(): String = "TimerReady ${timeToLabel(value)}"
        }

        data object StartCountDown : SideEffect()

        data object Restarting : SideEffect()

        data object Pause : SideEffect()

        data object Stop : SideEffect()

        data object Reset : SideEffect()
    }
}