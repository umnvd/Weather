package com.umnvd.weather.screens

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

interface AssistedViewModelFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}

class SavedStateViewModelsFactory @Inject constructor(
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    private val assistedFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards AssistedViewModelFactory<out ViewModel>>
) {

    fun create(
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val viewModel = createViewModel(modelClass)
                    ?: createAssistedViewModel(modelClass, handle)
                    ?: throw IllegalArgumentException("Unknown viewModel class $modelClass")

                return viewModel as T
            }

        }
    }

    private fun <T : ViewModel?> createViewModel(modelClass: Class<T>): ViewModel? {
        val creator = viewModelProviders[modelClass]
            ?: viewModelProviders.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: return null

        return creator.get()
    }

    private fun <T : ViewModel?> createAssistedViewModel(
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): ViewModel? {
        val creator = assistedFactories[modelClass]
            ?: assistedFactories.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: return null

        return creator.create(handle)
    }

}