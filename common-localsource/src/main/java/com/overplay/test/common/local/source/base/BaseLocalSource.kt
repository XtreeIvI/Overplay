package com.overplay.test.common.local.source.base

import android.content.SharedPreferences
import com.overplay.test.common.local.source.delegate.LocalSourceDelegate
import com.overplay.test.common.local.source.delegate.LocalSourceProvider

open class BaseLocalSource(
    protected val sharedPreferences: SharedPreferences,
) : LocalSourceDelegate by LocalSourceProvider(
    sharedPreferences
)