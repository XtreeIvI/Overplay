package com.overplay.test.feature.gyroscope.impl

import android.content.Context
import com.kircherelectronics.fsensor.observer.SensorSubject
import com.kircherelectronics.fsensor.sensor.gyroscope.GyroscopeSensor
import com.overplay.test.feature.gyroscope.DeviceRotationManager
import com.overplay.test.feature.gyroscope.model.RotationData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.util.*

internal class DeviceRotationManagerImpl(context: Context) : DeviceRotationManager {

    private var instanceCounter = 0

    private val sensorManager = GyroscopeSensor(context)

    override val currentDeviceRotation: RotationData
        get() = (deviceRotationFlow as StateFlow<RotationData>).value

    @get:Synchronized
    override val deviceRotationFlow: Flow<RotationData>
        get() = callbackFlow {
            val listener = SensorSubject.SensorObserver { values ->
                values ?: return@SensorObserver
                launch {
                    send(
                        RotationData(
                            x = values[1],
                            y = values[2],
                            z = values[0]
                        )
                    )
                }
            }

            trySendBlocking(RotationData())
            if (instanceCounter == 0) sensorManager.start()
            if (instanceCounter >= 0) instanceCounter++

            sensorManager.register(listener)

            awaitClose {
                sensorManager.unregister(listener)
                if (instanceCounter > 0) instanceCounter--
                if (instanceCounter == 0) sensorManager.stop()
            }
        }
}