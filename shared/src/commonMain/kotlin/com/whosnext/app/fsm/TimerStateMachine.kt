package com.whosnext.app.fsm

import co.touchlab.kermit.Logger
import com.whosnext.app.fsm.core.StateMachine
import com.whosnext.app.fsm.core.StateMachineLogDecorator
import com.whosnext.app.fsm.core.cache.StateMachineCache
import com.whosnext.app.fsm.core.cache.StateMachineCacheBehavior
import com.whosnext.app.fsm.core.cache.StateMachineSnapshot
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class TimerStateMachine(
    shouldRestoreState: Boolean = true,
    onTransition: (sideEffect: TimerState.SideEffect) -> Unit,
    onRestored: (state: TimerState.State) -> Unit
) : StateMachineLogDecorator<TimerState.State, TimerState.Event, TimerState.SideEffect>,
    StateMachineCacheBehavior<TimerState.State, TimerState.Event, TimerState.SideEffect>,
    KoinComponent {

    override val stateMachineName: String = "TimerStateMachine"
    override val logger = Logger.withTag(stateMachineName)
    override val disableLogs: Boolean = false

    private val cache = StateMachineCache(stateMachineName, get())
    private val stateMachine = StateMachine.create<TimerState.State, TimerState.Event, TimerState.SideEffect> {
        val snapshot: StateMachineSnapshot<TimerState.State, TimerState.Event>? = if (shouldRestoreState) cache.restoreState() else null
        val initialState = snapshot?.state ?: TimerState.State.SettingTimer
        initialState(initialState).apply {
            logInitState(initialState)
            onRestored(initialState)
        }

        state<TimerState.State.Idle> {
            on<TimerState.Event.OnSetTimer> {
                logState(this, TimerState.Event.OnSetTimer)
                transitionTo(TimerState.State.SettingTimer, TimerState.SideEffect.SetTimer)
            }
            on<TimerState.Event.OnStart> {
                logState(this, TimerState.Event.OnStart)
                transitionTo(TimerState.State.CountingDown, TimerState.SideEffect.StartCountDown)
            }
            on<TimerState.Event.OnReset> {
                logState(this, TimerState.Event.OnReset)
                transitionTo(TimerState.State.Idle, TimerState.SideEffect.Reset)
            }
        }
        state<TimerState.State.SettingTimer> {
            on<TimerState.Event.OnTimerSet> {
                logState(this, TimerState.Event.OnTimerSet(it.value))
                transitionTo(TimerState.State.Idle, TimerState.SideEffect.TimerReady(it.value))
            }
        }
        state<TimerState.State.CountingDown> {
            on<TimerState.Event.OnPause> {
                logState(this, TimerState.Event.OnPause)
                transitionTo(TimerState.State.Paused, TimerState.SideEffect.Pause)
            }
            on<TimerState.Event.OnStop> {
                logState(this, TimerState.Event.OnStop)
                transitionTo(TimerState.State.Idle, TimerState.SideEffect.Stop)
            }
            on<TimerState.Event.OnFinish> {
                logState(this, TimerState.Event.OnFinish)
                transitionTo(TimerState.State.Restarting, TimerState.SideEffect.Restarting)
            }
        }
        state<TimerState.State.Paused> {
            on<TimerState.Event.OnStart> {
                logState(this, TimerState.Event.OnStart)
                transitionTo(TimerState.State.CountingDown, TimerState.SideEffect.StartCountDown)
            }
            on<TimerState.Event.OnStop> {
                logState(this, TimerState.Event.OnStop)
                transitionTo(TimerState.State.Idle, TimerState.SideEffect.Stop)
            }
        }
        state<TimerState.State.Restarting> {
            on<TimerState.Event.OnStart> {
                logState(this, TimerState.Event.OnStart)
                transitionTo(TimerState.State.CountingDown, TimerState.SideEffect.StartCountDown)
            }
        }

        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
            with(validTransition) {
                logTransition(this)
                cache.saveState(StateMachineSnapshot(fromState, event))
                onTransition((sideEffect as TimerState.SideEffect))
            }
        }
    }

    init {
        handleCache(shouldRestoreState)
    }

    private fun handleCache(shouldRestoreState: Boolean) {
        if (shouldRestoreState) {
            if (!restoreStateIfAvailable()) {
                clearSavedState()
            }
        } else {
            clearSavedState()
        }
    }

    fun currentState(): TimerState.State = stateMachine.state

    fun transitionOn(event: TimerState.Event) {
        stateMachine.transition(event)
    }

    private fun restoreStateIfAvailable(): Boolean {
        val snapshot: StateMachineSnapshot<TimerState.State, TimerState.Event>? = cache.restoreState()
        return snapshot?.let { current ->
            cache.clear()
            logStateRestore(current.state, current.event)
            stateMachine.transition(current.event)
            true
        } ?: false
    }

    override fun clearSavedState() {
        cache.clear()
    }
}