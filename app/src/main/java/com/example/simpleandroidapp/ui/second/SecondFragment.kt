package com.example.simpleandroidapp.ui.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.repository.pojo.Beer
import android.widget.ArrayAdapter
import com.example.simpleandroidapp.repository.RepositoryResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*


class SecondFragment : Fragment() {

    private lateinit var viewModel: BeerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BeerViewModel::class.java)
        viewModel.fetchBeers()

        viewModel.getBeers().observe(this, Observer<RepositoryResult<List<Beer>>> {
            if (it is RepositoryResult.success) {
                    val arrayOf = it.data.map { it.name }
                    val adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        arrayOf
                    )

                    beer_list.adapter = adapter
                } else if ( it is RepositoryResult.error) {
                    Snackbar.make(view, R.string.no_beer, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, R.string.worth_waiting_for, Snackbar.LENGTH_LONG).show()
            }
        })

    }
}
