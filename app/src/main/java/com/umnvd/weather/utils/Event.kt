package com.umnvd.weather.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Event<T>(
    value: T
) {

    private var _value: T? = value

    fun get(): T? = _value.also { _value = null }

}

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias LiveEvent<T> = LiveData<Event<T>>
typealias EventListener<T> = (T) -> Unit

fun <T> MutableLiveEvent<T>.publishEvent(value: T) {
    this.value = Event(value)
}

fun <T> LiveEvent<T>.observeEvent(lifecycleOwner: LifecycleOwner, listener: EventListener<T>) {
    this.observe(lifecycleOwner) {
        it.get()?.let(listener)
    }
}

