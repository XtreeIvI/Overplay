package com.overplay.test.common.ui.ext

import android.view.View
import com.overplay.test.common.ui.util.NonNullDelegate
import kotlin.reflect.KProperty

fun <T : Any> View.invalidateDelegate(
    initialValue: T,
    afterSetPredicate: ((property: KProperty<*>, oldValue: T, newValue: T) -> Unit)? = null
): NonNullDelegate<T> = NonNullDelegate(initialValue) { property, oldValue, newValue ->
    afterSetPredicate?.invoke(property, oldValue, newValue)
    invalidate()
}

fun <T : Any> View.requestLayoutDelegate(
    initialValue: T,
    afterSetPredicate: ((property: KProperty<*>, oldValue: T, newValue: T) -> Unit)? = null
): NonNullDelegate<T> = NonNullDelegate(initialValue) { property, oldValue, newValue ->
    afterSetPredicate?.invoke(property, oldValue, newValue)
    requestLayout()
}

fun View.scale(scale: Float = 1.0F) {
    scaleX = scale
    scaleY = scale
}