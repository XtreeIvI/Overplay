package com.overplay.test.data.sessioncounter.local.source

import kotlinx.coroutines.flow.Flow

interface SessionCounterLocalSource {

    var sessionCounter: Int

    val sessionCounterFlow: Flow<Int>

    fun clearSessionCounter()

    companion object {
        const val SHARED_PREFS_NAME = "session_counter"
    }
}