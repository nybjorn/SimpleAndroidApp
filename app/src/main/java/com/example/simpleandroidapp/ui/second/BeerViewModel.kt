package com.example.simpleandroidapp.ui.second

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleandroidapp.repository.BeerRepository
import com.example.simpleandroidapp.repository.RepositoryResult
import com.example.simpleandroidapp.repository.pojo.Beer
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import kotlinx.coroutines.launch

class BeerViewModel @ViewModelInject constructor(
    private val beerRepository: BeerRepository,
    private val objectBox: BoxStore,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
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
