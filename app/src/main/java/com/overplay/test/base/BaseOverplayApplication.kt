package com.overplay.test.base

import android.app.Application
import androidx.annotation.CallSuper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class BaseOverplayApplication : Application() {

    protected open val diModules: List<Module> = emptyList()

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        initializeKoin()

    }

    private fun initializeKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseOverplayApplication)
            modules(diModules)
        }
    }
}