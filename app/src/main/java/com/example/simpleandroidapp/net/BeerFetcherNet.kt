package com.example.simpleandroidapp.net

import com.example.simpleandroidapp.repository.pojo.Beer
import retrofit2.http.GET

interface BeerFetcherNet {

    @GET("v2/beers")
    suspend fun fetchBeers(): List<Beer>
}
