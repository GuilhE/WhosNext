package com.whosnext.app.fsm.core.cache

import kotlinx.serialization.Serializable

@Serializable
internal data class StateMachineSnapshot<STATE, EVENT>(val state: STATE, val event: EVENT)