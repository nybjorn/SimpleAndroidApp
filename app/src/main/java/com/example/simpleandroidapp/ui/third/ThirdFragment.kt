package com.example.simpleandroidapp.ui.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.repository.pojo.Beer
import kotlinx.android.synthetic.main.fragment_third.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ThirdFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        one_beer_title.text = beer.name
    }
}
