package com.overplay.test.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.overplay.test.common.ui.base.BaseViewModel
import com.overplay.test.data.sessioncounter.repository.SessionCounterRepository
import com.overplay.test.feature.gyroscope.DeviceRotationManager
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

internal class MainViewModel(
    sessionCounterRepository: SessionCounterRepository,
    deviceRotationManager: DeviceRotationManager,
) : BaseViewModel() {

    val sessionCounterLiveData: LiveData<Int> = sessionCounterRepository
        .sessionCounterFlow
        .distinctUntilChanged()
        .asLiveData()

    val yRotationLiveData: LiveData<Float> = deviceRotationManager
        .deviceRotationFlow
        .map {
            when (val yDegree = it.yDegree) {
                in 0.0F..180.0F -> 0.0F - yDegree
                else -> 360.0F - yDegree
            }
        }
        .distinctUntilChanged()
        .asLiveData()
}