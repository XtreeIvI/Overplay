package com.overplay.test.common.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

abstract class BaseActivity<ViewBinding : androidx.viewbinding.ViewBinding> : AppCompatActivity() {

    abstract val viewBinding: ViewBinding

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        configureUi()
        configureObserver()
    }

    protected open fun configureUi() {
        // Stub.
    }

    protected open fun configureObserver() {
        // Stub
    }

    //region Convenient Observe Methods
    @MainThread
    protected inline fun <reified T> LiveData<T>.observe(noinline observer: (T) -> Unit) {
        this.observe(this@BaseActivity, observer)
    }
    //endregion
}