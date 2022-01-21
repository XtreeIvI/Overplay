package com.overplay.test.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.overplay.test.common.ui.base.BaseViewModel
import com.overplay.test.data.sessioncounter.repository.SessionCounterRepository
import kotlinx.coroutines.flow.distinctUntilChanged

internal class MainViewModel(
    sessionCounterRepository: SessionCounterRepository,
) : BaseViewModel() {

    val sessionCounterLiveData: LiveData<Int> = sessionCounterRepository
        .sessionCounterFlow
        .distinctUntilChanged()
        .asLiveData()
}