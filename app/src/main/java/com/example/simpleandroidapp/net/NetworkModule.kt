package com.example.simpleandroidapp.net

import com.example.simpleandroidapp.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideBeers(get()) }
    single { provideRetroFit() }
}

fun provideBeers(retrofit: Retrofit): BeerFetcherNet {
    return retrofit.create(BeerFetcherNet::class.java)
}

fun provideRetroFit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BEER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
