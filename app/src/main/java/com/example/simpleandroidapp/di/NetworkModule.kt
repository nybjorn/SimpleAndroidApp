package com.example.simpleandroidapp.di

import android.content.Context
import com.example.simpleandroidapp.BuildConfig
import com.example.simpleandroidapp.net.BeerFetcherNet
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
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

    @Provides
    fun provideBeers(retrofit: Retrofit): BeerFetcherNet {
        return retrofit.create(BeerFetcherNet::class.java)
    }

    @Provides
    fun provideRetroFit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BEER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
