package com.overplay.test.feature.gyroscope

import com.overplay.test.feature.gyroscope.model.RotationData
import kotlinx.coroutines.flow.Flow

interface DeviceRotationManager {

    val currentDeviceRotation: RotationData

    val deviceRotationFlow: Flow<RotationData>
}