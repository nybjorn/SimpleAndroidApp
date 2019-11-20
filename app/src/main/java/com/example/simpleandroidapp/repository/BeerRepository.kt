package com.example.simpleandroidapp.repository

import com.example.simpleandroidapp.net.BeerFetcherNet
import com.example.simpleandroidapp.repository.pojo.Beer
import io.objectbox.Box
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.dsl.module
import java.lang.Exception
import kotlin.random.Random

val beerModule = module { factory { BeerRepository(get(), get()) } }

@Suppress("MagicNumber")
class BeerRepository(private val beerWebService: BeerFetcherNet, val objectBox: Box<Beer>) {
    /*
    val BASE_URL = "https://api.punkapi.com/"

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BeerFetcherNet::class.java)
    }*/

    private val randomSeed = 4711
    private val minResponseDelay = 1000L
    private val maxResponseDelay = 3000L

    // https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter/issues/3
    @Suppress("TooGenericExceptionCaught")
    suspend fun fetchBeers(): RepositoryResult<List<Beer>> {
        return try {
            delay(Random(randomSeed).nextLong(minResponseDelay, maxResponseDelay))
            val data = beerWebService.fetchBeers()
            if (data.isNotEmpty()) {
                saveToDatabase(data)
            }
            RepositoryResult.Success(data)
        } catch (e: Exception) {
            RepositoryResult.Error(e.message.toString())
        }
    }

    private fun saveToDatabase(data: List<Beer>) {
        CoroutineScope(Dispatchers.IO).launch {
            objectBox.removeAll() // deleting and inserting data to avoid sync issues
            objectBox.put(data)
        }
    }
}
