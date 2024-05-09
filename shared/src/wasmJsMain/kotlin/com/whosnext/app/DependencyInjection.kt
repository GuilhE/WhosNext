package com.whosnext.app

import co.touchlab.kermit.Logger
import com.whosnext.app.viewmodel.TimerViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun platformModule() = module {
    factory { TimerViewModel() }
    factory(named("NoStateRestore")) { TimerViewModel(false) }
}

@Suppress("unused")
object ViewModels : KoinComponent {
    fun timerViewModel() = get<TimerViewModel>().also {
        Logger.d("Kotlin\tobject ${it.toJsReference()}")
    }

    fun timerViewModelWithoutStateRestore() = get<TimerViewModel>(named("NoStateRestore"))
}