package com.overplay.test.data.sessioncounter.repository

import kotlinx.coroutines.flow.Flow

// For simplicity of this example I will skip extra domain (business) layer and respective class (UseCase/Interactor)
interface SessionCounterRepository {

    val sessionCounter: Int

    val sessionCounterFlow: Flow<Int>

    fun clearSessionCounter()
}