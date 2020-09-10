package com.example.simpleandroidapp.ui.second

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.simpleandroidapp.repository.RepositoryResult
import com.example.simpleandroidapp.repository.pojo.Beer

@Composable
@SuppressWarnings("MagicNumber", "FunctionNaming")
fun BeerListingScreen(navController: NavController) {
    val beerVm = viewModel<BeerViewModel>()
    Column {

        val beers by beerVm.getBeers().observeAsState()
        when (beers) {
            is RepositoryResult.Success -> {
                val arrayOf = (beers as RepositoryResult.Success<List<Beer>>).data.map { it }
                LazyColumnFor(arrayOf) { beer ->
                    Row(modifier = Modifier.clickable(onClick = {
                        val toThirdFragment = SecondFragmentDirections.actionSecondFragmentToThirdFragment(beer)
                        navController.navigate(toThirdFragment)
                    })) {
                        Text(text = beer.name.toString())
                        }
                    }
                    Spacer(modifier = Modifier.height(23.dp))
                }
            is RepositoryResult.Error -> {
                androidx.compose.material.Snackbar() {
                    Text("No beer")
                }
                          }
            is RepositoryResult.Loading -> {
                androidx.compose.material.Snackbar() {
                    Text("Loading")
                }
            }
            is RepositoryResult.IntermediateError -> {
                CircularProgressIndicator()
                androidx.compose.material.Snackbar() {
                    Text("No beer")
                }
            }
        }

        Text("sads")
    }
}
