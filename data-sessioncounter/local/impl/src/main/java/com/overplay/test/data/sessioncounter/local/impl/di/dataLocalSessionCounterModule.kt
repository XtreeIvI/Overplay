package com.overplay.test.data.sessioncounter.local.impl.di

import android.content.Context
import com.overplay.test.data.sessioncounter.local.impl.source.SessionCounterLocalSourceImpl
import com.overplay.test.data.sessioncounter.local.source.SessionCounterLocalSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataLocalSessionCounterModule = module {

    single<SessionCounterLocalSource> {
        SessionCounterLocalSourceImpl(
            sharedPreferences = androidContext().getSharedPreferences(
                SessionCounterLocalSource.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            ),
        )
    }
}