package com.michaelagustian.boxoffice.features.moviedetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelagustian.boxoffice.databinding.MovieDetailFragmentBinding
import com.michaelagustian.boxoffice.utils.setupActionBar
import com.michaelagustian.boxoffice.utils.setupToast
import kotlinx.android.synthetic.main.movie_detail_fragment.*

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieDetailFragment : Fragment() {

    private lateinit var binding: MovieDetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MovieDetailActivity).viewModel
            setLifecycleOwner(this@MovieDetailFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setupActionBar(toolbar_movie_detail, true)
        setupToast()
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.start(arguments?.getInt(ARGUMENT_MOVIE_ID))
    }

    private fun setupToast() {
        binding.viewModel?.let {
            view?.setupToast(this, it.toastMessage)
        }
    }

    companion object {
        const val ARGUMENT_MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: Int) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGUMENT_MOVIE_ID, movieId)
            }
        }
    }
}