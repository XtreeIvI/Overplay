package com.overplay.test.common.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    //region Live Data convenience functions
    /**
     * I prefer this way of casting internally instead of backing field duplication (_name & name)
     */
    protected infix fun <T> LiveData<T>.setValue(value: T) {
        (this as MutableLiveData<T>).value = value
    }

    protected infix fun <T> LiveData<T>.postValue(value: T) {
        (this as MutableLiveData<T>).postValue(value)
    }
    //endregion Live Data convenience functions
}