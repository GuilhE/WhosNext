package com.whosnext.app.fsm.core.cache

internal interface StateMachineCacheBehavior<STATE : Any, EVENT : Any, SIDE_EFFECT : Any> {
    fun clearSavedState()
}