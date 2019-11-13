package com.example.simpleandroidapp.ui.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleandroidapp.repository.BeerRepository
import com.example.simpleandroidapp.repository.RepositoryResult
import com.example.simpleandroidapp.repository.pojo.Beer
import kotlinx.coroutines.launch

class BeerViewModel : ViewModel() {
    private val beers = MutableLiveData<RepositoryResult<List<Beer>>>()
    private val beerRepository = BeerRepository()


    fun fetchBeers() {
        beers.value = RepositoryResult.loading()
        viewModelScope.launch {
            beers.value = beerRepository.fetchBeers()
        }
    }

    fun getBeers(): MutableLiveData<RepositoryResult<List<Beer>>> {
        return beers
    }
}
