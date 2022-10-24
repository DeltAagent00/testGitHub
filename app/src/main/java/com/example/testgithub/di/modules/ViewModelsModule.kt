package com.example.testgithub.di.modules

import com.example.testgithub.ui.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val viewModelsModule by lazy {
    DI.Module("viewModels") {
        bind<MainViewModel>() with provider {
            MainViewModel(
                instance()
            )
        }
    }
}