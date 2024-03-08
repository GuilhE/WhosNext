package com.whosnext.app.viewmodel

import com.russhwolf.settings.Settings
import com.whosnext.app.viewmodel.core.UiStateCache
import com.whosnext.app.viewmodel.core.UiStateCacheProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * Extension function to create [TimerSavedStateContainer].
 * @param stateFlow Flow to collect and save states from.
 * @param onRestore Callback with the cached state to be restored.
 */
internal fun CoroutineScope.savedStateContainer(
    stateFlow: Flow<TimerUiState>,
    onRestore: (TimerUiState) -> Unit
) = TimerSavedStateContainer(this, stateFlow, onRestore)

/**
 * Implementation of [UiStateCacheProxy]. It will use [UiStateCache] to save and restore states.
 * @param scope Where [stateFlow] will collect new state emissions.
 * @param stateFlow Flow to collect and save states from.
 * @param onRestore Callback with the cached state to be restored.
 */
internal class TimerSavedStateContainer(
    scope: CoroutineScope,
    stateFlow: Flow<TimerUiState>,
    private val onRestore: (TimerUiState) -> Unit
) : UiStateCacheProxy<TimerUiState>(scope, stateFlow), KoinComponent {

    private val cache: UiStateCache = UiStateCache("UiState", get<Settings>())

    override fun saveState(state: TimerUiState) {
        cache.saveState(state)
    }

    override fun restoreState() {
        cache.restoreState<TimerUiState>()?.let { cached -> onRestore(cached) }
    }

    override fun clearState() {
        cache.clearState()
    }
}