package com.example.testgithub.di

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import org.kodein.di.DIAware
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.type.erased
import org.kodein.type.generic

class ViewModelFactory(private val injector: DirectDI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.Instance(erased(modelClass))
    }
}

class SavedViewModelFactory(
    owner: SavedStateRegistryOwner,
    arguments: Bundle = bundleOf(),
    private val injector: DirectDI
) : AbstractSavedStateViewModelFactory(owner, arguments) {

    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return injector.Instance(generic(), erased(modelClass), arg = handle)
    }
}

fun <T> T.getFactoryInstance(saveState: Boolean = false): ViewModelProvider.Factory where T : DIAware, T : LifecycleOwner {
    if (saveState) {
        return SavedViewModelFactory(this as SavedStateRegistryOwner, injector = direct)
    }
    return ViewModelFactory(direct)
}

inline fun <reified VM : ViewModel, T> T.activityViewModel(
    saveState: Boolean = false,
): Lazy<VM> where T : DIAware, T : ComponentActivity {
    return viewModels { getFactoryInstance(saveState = saveState) }
}