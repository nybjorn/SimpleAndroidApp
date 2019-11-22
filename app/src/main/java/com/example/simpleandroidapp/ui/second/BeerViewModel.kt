package com.example.simpleandroidapp.ui.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleandroidapp.repository.BeerRepository
import com.example.simpleandroidapp.repository.RepositoryResult
import com.example.simpleandroidapp.repository.dao.ObjectBox
import com.example.simpleandroidapp.repository.pojo.Beer
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import kotlinx.coroutines.launch
import org.koin.dsl.module

val beerViewModule = module {
    factory { BeerViewModel(get(), get()) }
}

class BeerViewModel(val beerRepository: BeerRepository, val objectBox: BoxStore) : ViewModel() {
    private val beers = MutableLiveData<RepositoryResult<List<Beer>>>()

    private var beerLiveData: ObjectBoxLiveData<Beer>? = null

    fun fetchBeers() {
        beers.value = RepositoryResult.Loading()
        viewModelScope.launch {
            beers.value = beerRepository.fetchBeers()
        }
    }

    fun getBeers(): MutableLiveData<RepositoryResult<List<Beer>>> {
        return beers
    }

    fun fetchBeerLiveData(): ObjectBoxLiveData<Beer> {
        if (beerLiveData == null) { // query all notes, sorted a-z by their text
            beerLiveData = ObjectBoxLiveData<Beer>(objectBox.boxFor(Beer::class.java).query().build())
        }
        return beerLiveData!!
    }
}
