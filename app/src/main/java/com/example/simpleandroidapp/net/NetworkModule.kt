package com.example.simpleandroidapp.net

import android.app.Application
import com.example.simpleandroidapp.BuildConfig
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideBeers(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetroFit(get()) }
}

fun provideOkHttpClient(context: Application): OkHttpClient {
    return if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                FlipperOkhttpInterceptor(
                    AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)
                )
            )
            .build()
    } else {
        OkHttpClient()
    }
}

fun provideBeers(retrofit: Retrofit): BeerFetcherNet {
    return retrofit.create(BeerFetcherNet::class.java)
}

fun provideRetroFit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BEER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}
