package com.overplay.test.feature.main.di

import com.overplay.test.feature.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureMainModule = module {

    viewModel {
        MainViewModel(
            sessionCounterRepository = get(),
            deviceRotationManager = get(),
        )
    }
}