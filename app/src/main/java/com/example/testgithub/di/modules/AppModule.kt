package com.example.testgithub.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val appModule by lazy {
    DI.Module("app") {
        bind {
            singleton {
                GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            }
        }
    }
}