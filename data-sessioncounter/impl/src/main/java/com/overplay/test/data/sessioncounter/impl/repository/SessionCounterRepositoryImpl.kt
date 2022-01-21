package com.overplay.test.data.sessioncounter.impl.repository

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.overplay.test.data.sessioncounter.local.source.SessionCounterLocalSource
import com.overplay.test.data.sessioncounter.repository.SessionCounterRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class SessionCounterRepositoryImpl(
    private val localSource: SessionCounterLocalSource,
) : SessionCounterRepository {

    private val repositoryScope = CoroutineScope(Job() + Dispatchers.Default)

    private val sessionDuration by lazy { 10.toDuration(DurationUnit.MINUTES) }

    private var sessionBackgroundExpirationJob: Job? = null

    private val defaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            //App in background
            scheduleSessionBackgroundExpirationJob()
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            //App in foreground
            cancelSessionBackgroundExpirationJob()
        }

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            // Increment counter at Application creation (process start)
            incrementSessionCounter()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            // Unfortunately, this function is never called for ProcessLifecycleOwner
            // However till this time application is dead and there is no necessity to cancel the scope
            // So line of code below could be totally omitted in this particular case.
            repositoryScope.cancel()
        }
    }

    override val sessionCounter: Int
        get() = localSource.sessionCounter

    override val sessionCounterFlow: Flow<Int> by localSource::sessionCounterFlow

    fun incrementSessionCounter() {
        localSource.sessionCounter++
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(defaultLifecycleObserver)
    }

    override fun clearSessionCounter() = localSource.clearSessionCounter()

    private fun scheduleSessionBackgroundExpirationJob() {
        cancelSessionBackgroundExpirationJob()
        sessionBackgroundExpirationJob = repositoryScope.launch {
            delay(sessionDuration)
            incrementSessionCounter()
        }
    }

    private fun cancelSessionBackgroundExpirationJob() {
        sessionBackgroundExpirationJob?.apply {
            if (isActive) cancel()
        }
    }
}