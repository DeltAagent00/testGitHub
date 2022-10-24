package com.example.testgithub.ui

import android.app.Application
import com.example.testgithub.di.modules.appModule
import com.example.testgithub.di.modules.networkModule
import com.example.testgithub.di.modules.repoModule
import com.example.testgithub.di.modules.viewModelsModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class AppApplication: Application(), DIAware {
    override val di: DI
        get() = DI.from(listOf(
            androidXModule(this),
            appModule,
            networkModule,
            repoModule,
            viewModelsModule
        ))
}