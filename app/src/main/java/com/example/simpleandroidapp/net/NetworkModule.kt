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
    single { provideRetroFit(get()) }
}

fun provideBeers(retrofit: Retrofit): BeerFetcherNet {
    return retrofit.create(BeerFetcherNet::class.java)
}

fun provideRetroFit(context: Application): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            FlipperOkhttpInterceptor(AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID))
        )
    .build()
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BEER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}
