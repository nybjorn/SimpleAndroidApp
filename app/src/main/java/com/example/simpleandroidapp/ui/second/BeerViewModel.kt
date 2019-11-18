package com.example.simpleandroidapp.ui.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleandroidapp.repository.BeerRepository
import com.example.simpleandroidapp.repository.RepositoryResult
import com.example.simpleandroidapp.repository.pojo.Beer
import kotlinx.coroutines.launch
import org.koin.dsl.module

val beerViewModule = module {
    factory { BeerViewModel(get()) }
}

class BeerViewModel(val beerRepository: BeerRepository) : ViewModel() {
    private val beers = MutableLiveData<RepositoryResult<List<Beer>>>()

    fun fetchBeers() {
        beers.value = RepositoryResult.Loading()
        viewModelScope.launch {
            beers.value = beerRepository.fetchBeers()
        }
    }

    fun getBeers(): MutableLiveData<RepositoryResult<List<Beer>>> {
        return beers
    }
}
