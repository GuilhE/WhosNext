package com.whosnext.app.viewmodel

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.whosnext.app.fsm.TimerState
import com.whosnext.app.fsm.TimerStateMachine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @param stateRestoreEnabled if true (default) will enable Ui State and FSM State restoration
 */
class TimerViewModel(stateRestoreEnabled: Boolean = true) : KMMViewModel() {

    private val _uiState = MutableStateFlow(viewModelScope, TimerUiState())
    private val savedStateContainer: TimerSavedStateContainer? =
        if (stateRestoreEnabled) {
            viewModelScope.coroutineScope.savedStateContainer(_uiState) { state ->
                _uiState.update { state }
            }
        } else null

    private var lastInt: Int = 0
    private val randomInts = mutableSetOf<Int>()
    private val stateMachine = TimerStateMachine(stateRestoreEnabled,
        onTransition = { effect ->
            when (effect) {
                is TimerState.SideEffect.SetTimer -> _uiState.update { it.settingTimer() }
                is TimerState.SideEffect.TimerReady -> _uiState.update { it.setTime(effect.value) }
                is TimerState.SideEffect.StartCountDown -> play()
                is TimerState.SideEffect.Restarting -> restartAnimation()
                is TimerState.SideEffect.Pause -> _uiState.update { it.pause() }
                is TimerState.SideEffect.Stop -> _uiState.update { it.stop() }
                is TimerState.SideEffect.Reset -> {
                    _uiState.update { it.reset() }
                    clearSavedStates()
                }
            }
        },
        onRestored = {
            savedStateContainer?.restoreState()
        }
    )

    /**
     * [TimerUiState] flow to observe ui state changes
     */
    @NativeCoroutinesState
    val uiState = _uiState.asStateFlow()

    init {
        if (!stateRestoreEnabled) {
            clearSavedStates()
        }
    }

    private fun clearSavedStates() {
        savedStateContainer?.clearState()
        stateMachine.clearSavedState()
    }

    private fun restartAnimation() {
        viewModelScope.coroutineScope.launch {
            if (randomInts.isEmpty()) {
                var temp = listOf(1, 2, 3, 4, 5, 6).shuffled()
                while (temp.first() == lastInt) {
                    temp = temp.shuffled()
                }
                randomInts.addAll(temp)
            }
            _uiState.update { state ->
                state.restart(
                    randomInts
                        .first { int -> int != state.backgroundIndex }
                        .also {
                            randomInts.remove(it)
                            if (randomInts.isEmpty()) {
                                lastInt = it
                            }
                        }
                )
            }
            delay(RESTART_DELAY)
            stateMachine.transitionOn(TimerState.Event.OnStart)
        }
    }

    private fun play() {
        viewModelScope.coroutineScope.launch {
            try {
                while (stateMachine.currentState() == TimerState.State.CountingDown) {
                    with(uiState.value) {
                        if (progress == 100f) {
                            stateMachine.transitionOn(TimerState.Event.OnFinish)
                            return@launch
                        } else {
                            val e = elapsed + 0.1f
                            _uiState.update {
                                it.play().copy(progress = normalize(e, 0f, value.toFloat(), 0f, 100f), elapsed = e)
                            }
                        }
                        delay(STEP_DELAY)
                    }
                }
            } catch (ignore: NullPointerException) {
                //On Android sometimes VM restores faster then FSM, this insures at least it "pauses" instead of crashing.
                delay(10)
                play()
            }
        }
    }

    /**
     * Sends [TimerState.Event.OnSetTimer] event to FSM
     */
    fun settingTime() {
        stateMachine.transitionOn(TimerState.Event.OnSetTimer)
    }

    /**
     * Sends [TimerState.Event.OnTimerSet] event to FSM
     * @param seconds
     */
    fun setTime(seconds: Int) {
        stateMachine.transitionOn(TimerState.Event.OnTimerSet(seconds))
    }

    /**
     * Sends [TimerState.Event.OnStart] event to FSM
     */
    fun start() {
        stateMachine.transitionOn(TimerState.Event.OnStart)
    }

    /**
     * Sends [TimerState.Event.OnPause] event to FSM
     */
    fun pause() {
        stateMachine.transitionOn(TimerState.Event.OnPause)
    }

    /**
     * Sends [TimerState.Event.OnStop] event to FSM
     */
    fun stop() {
        stateMachine.transitionOn(TimerState.Event.OnStop)
    }

    /**
     * Sends [TimerState.Event.OnReset] event to FSM
     */
    fun reset() {
        stateMachine.transitionOn(TimerState.Event.OnReset)
    }

    companion object {
        private const val RESTART_DELAY: Long = 3000L
        private const val STEP_DELAY: Long = 100L
    }
}
