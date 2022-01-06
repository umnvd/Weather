package com.umnvd.weather.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import kotlin.reflect.KClass

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class InputOutput

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Computation

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)