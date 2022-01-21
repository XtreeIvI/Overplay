package com.overplay.test.data.sessioncounter.impl.di

import com.overplay.test.data.sessioncounter.impl.repository.SessionCounterRepositoryImpl
import com.overplay.test.data.sessioncounter.repository.SessionCounterRepository
import org.koin.dsl.module

val dataSessionCounterModule = module {

    single<SessionCounterRepository> {
        SessionCounterRepositoryImpl(
            localSource = get(),
        )
    }
}