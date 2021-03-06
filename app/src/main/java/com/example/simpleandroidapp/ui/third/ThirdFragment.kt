package com.example.simpleandroidapp.ui.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.repository.pojo.Beer
import com.example.simpleandroidapp.util.GlideApp
import kotlinx.android.synthetic.main.fragment_third.*

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
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Navigation.findNavController(view).currentDestination?.let {
            it.label = beer.name
        }
        beer.foodPairing?.let {
            one_beer_title.text = it.joinToString(separator = "\n") { food -> "* $food" }
        }

        if (beer.imageUrl.isNullOrEmpty()) {
            beer_image.visibility = View.GONE
        } else {
            GlideApp.with(view).load(beer.imageUrl).into(beer_image)
        }
    }
}
