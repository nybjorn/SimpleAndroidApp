package com.example.simpleandroidapp.ui.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation
import com.example.simpleandroidapp.repository.pojo.Beer

class ThirdFragment : Fragment() {

    lateinit var beer: Beer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        arguments?.let {
            beer = ThirdFragmentArgs.fromBundle(it).OneBeer
        }
        val view = ComposeView(requireContext()).apply {
            setContent {
                SingleBeerScreen(
                    name = beer.name!!,
                    beer.foodPairing!!.joinToString(separator = "\n") { food -> "* $food" },
                    imageUrl = beer.imageUrl!!
                )
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Navigation.findNavController(view).currentDestination?.let {
            it.label = beer.name
        }
    }
}
