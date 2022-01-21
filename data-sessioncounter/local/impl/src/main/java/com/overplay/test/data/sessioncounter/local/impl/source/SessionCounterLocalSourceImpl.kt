package com.overplay.test.data.sessioncounter.local.impl.source

import android.content.SharedPreferences
import com.overplay.test.common.local.source.base.BaseLocalSource
import com.overplay.test.data.sessioncounter.local.source.SessionCounterLocalSource
import kotlinx.coroutines.flow.Flow

internal class SessionCounterLocalSourceImpl(
    sharedPreferences: SharedPreferences,
) : BaseLocalSource(sharedPreferences),
    SessionCounterLocalSource {

    override var sessionCounter: Int
        @Synchronized
        set(value) {
            sharedPreferences
                .edit()
                .putInt(KEY_SESSION_COUNTER, value)
                .apply()
        }
        @Synchronized
        get() = sharedPreferences.getInt(KEY_SESSION_COUNTER, 0)

    override val sessionCounterFlow: Flow<Int> by sharedPrefFlow(KEY_SESSION_COUNTER) { _, _ ->
        sessionCounter
    }

    override fun clearSessionCounter() {
        sessionCounter = 0
    }

    companion object {
        private const val KEY_SESSION_COUNTER = "session_counter"
    }
}