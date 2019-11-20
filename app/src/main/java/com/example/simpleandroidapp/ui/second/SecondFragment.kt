package com.example.simpleandroidapp.ui.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.repository.pojo.Beer
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.simpleandroidapp.repository.RepositoryResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchBeers()

        viewModel.getBeers().observe(this, Observer<RepositoryResult<List<Beer>>> {
            when (it) {
                is RepositoryResult.Success -> {
                    beer_progress.visibility = View.GONE
                    val arrayOf = it.data.map { it.name }
                    val adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        arrayOf
                    )

                    beer_list.adapter = adapter
                    beer_list.setOnItemClickListener { _, view, position, _ ->
                        val toThirdFragment =
                            SecondFragmentDirections.actionSecondFragmentToThirdFragment(it.data[position])
                        view.findNavController().navigate(toThirdFragment) }
                }
                is RepositoryResult.Error -> {
                    beer_progress.visibility = View.GONE
                    Snackbar.make(view, R.string.no_beer, Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    beer_progress.visibility = View.VISIBLE
                    Snackbar.make(view, R.string.worth_waiting_for, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
