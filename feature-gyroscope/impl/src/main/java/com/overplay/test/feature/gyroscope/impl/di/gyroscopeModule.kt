package com.overplay.test.feature.gyroscope.impl.di

import com.overplay.test.feature.gyroscope.DeviceRotationManager
import com.overplay.test.feature.gyroscope.impl.DeviceRotationManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val gyroscopeModule = module {
    single<DeviceRotationManager> {
        DeviceRotationManagerImpl(
            androidContext(),
        )
    }
}