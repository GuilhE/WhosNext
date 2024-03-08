package com.whosnext.app

import com.whosnext.app.viewmodel.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun platformModule() = module {
    viewModel { TimerViewModel() }
    viewModel(named("NoStateRestore")) { TimerViewModel(false) }
}