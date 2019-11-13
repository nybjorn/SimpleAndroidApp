package com.example.simpleandroidapp.ui.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleandroidapp.net.BeerFetcherNet
import com.example.simpleandroidapp.repository.BeerRepository
import com.example.simpleandroidapp.repository.pojo.Beer
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeerViewModel : ViewModel() {
    private val beers = MutableLiveData<List<Beer>>()
    private val beerRepository = BeerRepository()


    fun fetchBeers() {
        viewModelScope.launch {
            beers.value = beerRepository.fetchBeers()
        }
    }

    fun getBeers(): MutableLiveData<List<Beer>> {
        return beers;
    }
}
