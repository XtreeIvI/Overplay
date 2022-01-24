package com.overplay.test.common.ui.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class NonNullDelegate<T : Any>(
        initialValue: T,
        private val afterSetPredicate: ((property: KProperty<*>, oldValue: T, newValue: T) -> Unit)? = null
    ) : ReadWriteProperty<Any?, T> {
        private var value: T = initialValue

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            if (this.value == value) return
            val oldValue = this.value
            this.value = value
            afterSetPredicate?.invoke(property, oldValue, this.value)
        }
    }