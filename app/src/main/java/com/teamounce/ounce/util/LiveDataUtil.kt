package com.teamounce.ounce.util

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> MediatorLiveData<T>.addSourceList(
    vararg liveDataArgument: MutableLiveData<*>,
    onChanged: () -> T
) {
    liveDataArgument.forEach {
        this.addSource(it) { value = onChanged() }
    }
}