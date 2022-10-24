package com.example.testgithub.di.modules

import com.example.testgithub.BuildConfig
import com.example.testgithub.data.network.api.IApiService
import com.example.testgithub.data.network.error.parser.INetworkParserError
import com.example.testgithub.data.network.error.parser.NetworkParserErrorImpl
import com.example.testgithub.data.network.utils.INetworkUtils
import com.example.testgithub.data.network.utils.NetworkUtilsImpl
import com.google.gson.Gson
import okhttp3.CacheControl
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule by lazy {
    DI.Module("network") {
        bind<INetworkUtils>() with singleton {
            NetworkUtilsImpl(
                instance()
            )
        }
        bind<INetworkParserError>() with singleton {
            NetworkParserErrorImpl(
                instance()
            )
        }
        bind {
            singleton {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                interceptor
            }
        }

        bind {
            singleton {
                val httpLoggingInterceptor = instance<HttpLoggingInterceptor>()

                val dispatcher = Dispatcher()
                dispatcher.maxRequests = 1

                val okHttpBuilder = OkHttpClient.Builder()
                okHttpBuilder.addInterceptor { //cache
                        chain ->
                    val response = chain.proceed(chain.request())
                    // re-write response header to force use of cache
                    val cacheControl = CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build()
                    response.newBuilder()
                        .header("Cache-Control", cacheControl.toString())
                        .build()
                }
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                            .header("Content-Type", "application/json")

                        val request = requestBuilder
                            .method(original.method, original.body)
                            .build()

                        chain.proceed(request)
                    }
                    .dispatcher(dispatcher)

                    .connectTimeout(40, TimeUnit.SECONDS)
                    .writeTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)

                    .addNetworkInterceptor(httpLoggingInterceptor)

                okHttpBuilder.build()
            }
        }

        bind<Retrofit>() with singleton {
            val gson = instance<Gson>()
            val client = instance<OkHttpClient>()

            Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        bind<IApiService>() with singleton {
            val retrofit = instance<Retrofit>()

            retrofit.create(IApiService::class.java)
        }
    }
}