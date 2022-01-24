package com.overplay.test.common.ui.ext

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.use

@ColorInt
fun Context.getAttrColor(@AttrRes id: Int): Int {
    val attrs = intArrayOf(id)

    return obtainStyledAttributes(attrs).use {
        it.getColorOrThrow(0)
    }
}