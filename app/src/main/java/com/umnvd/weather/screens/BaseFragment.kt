package com.umnvd.weather.screens

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.umnvd.weather.appComponent
import com.umnvd.weather.di.AppComponent
import com.umnvd.weather.di.SavedStateViewModelsFactory
import dagger.Lazy
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {

    @Inject
    lateinit var savedStateViewModelsFactory: Lazy<SavedStateViewModelsFactory>

    abstract fun inject(appComponent: AppComponent)

    override fun onAttach(context: Context) {
        inject(context.appComponent)
        super.onAttach(context)
    }

}

inline fun <reified VM: ViewModel> BaseFragment.savedStateViewModels() = viewModels<VM> {
    savedStateViewModelsFactory.get().create(this, arguments)
}