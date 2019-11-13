package com.example.simpleandroidapp.repository

import com.example.simpleandroidapp.net.BeerFetcherNet
import com.example.simpleandroidapp.repository.pojo.Beer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class BeerRepository {

    val BASE_URL = "https://api.punkapi.com/"

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BeerFetcherNet::class.java)
    }

    // https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter/issues/3
    suspend fun fetchBeers(): List<Beer> {
        return try {
            webservice.fetchBeers()
        } catch (e : Exception) {
            emptyList()
        }
    }
}
