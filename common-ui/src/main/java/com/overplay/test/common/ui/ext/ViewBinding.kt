package com.overplay.test.common.ui.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.overplay.test.common.ui.R
import kotlin.reflect.full.functions

//region Activity View Binding
inline fun <reified T : ViewBinding> FragmentActivity.viewBindingBind(
    crossinline bind: (contentView: View) -> T = {
        T::class.functions.first { it.name == "bind" && it.parameters.size == 1 }.call(it) as T
    }
): Lazy<T> = object : Lazy<T> {
    private var viewBinding: T? = null
    override fun isInitialized(): Boolean = viewBinding != null
    override val value: T
        get() = viewBinding ?: bind(getContentView()).also { viewBinding = it }

    private fun FragmentActivity.getContentView(): View {
        return checkNotNull(findViewById<ViewGroup>(R.id.content).getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout res id."
        }
    }
}

inline fun <reified T : ViewBinding> FragmentActivity.viewBindingInflate(
    crossinline inflate: (layoutInflater: LayoutInflater) -> T = {
        T::class.functions.first { it.name == "inflate" && it.parameters.size == 1 }.call(it) as T
    }
): Lazy<T> = object : Lazy<T> {
    private var viewBinding: T? = null
    override fun isInitialized(): Boolean = viewBinding != null
    override val value: T
        get() = viewBinding ?: inflate(layoutInflater).also { viewBinding = it }
}
//endregion