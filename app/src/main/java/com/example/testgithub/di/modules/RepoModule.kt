package com.example.testgithub.di.modules

import com.example.testgithub.data.repositories.user.IUserRepo
import com.example.testgithub.data.repositories.user.UserRepoImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


val repoModule by lazy {
    DI.Module("repo") {
        bind<IUserRepo>() with singleton {
            UserRepoImpl(
                instance(),
                instance(),
                instance()
            )
        }
    }
}