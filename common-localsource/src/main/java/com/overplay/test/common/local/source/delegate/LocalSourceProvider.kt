package com.overplay.test.common.local.source.delegate

import android.content.SharedPreferences
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

internal open class LocalSourceProvider(
    protected val sharedPreferences: SharedPreferences
) : LocalSourceDelegate {

    override fun <T> sharedPrefFlow(
        key: String,
        onChanged: suspend (prefs: SharedPreferences, key: String) -> T
    ): Lazy<Flow<T>> {
        return lazy {
            getSharedPrefFlow(key, onChanged)
        }
    }

    override fun <T> getSharedPrefFlow(
        key: String,
        onChanged: suspend (prefs: SharedPreferences, key: String) -> T
    ): Flow<T> {
        return callbackFlow {
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
                if (key != changedKey) return@OnSharedPreferenceChangeListener
                launch {
                    trySendBlocking(onChanged(prefs, changedKey))
                }
            }

            trySendBlocking(onChanged(sharedPreferences, key))

            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

            awaitClose {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }
    }
}