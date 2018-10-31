package com.michaelagustian.boxoffice.features.movie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.michaelagustian.boxoffice.databinding.MovieFragmentBinding
import com.michaelagustian.boxoffice.utils.setupToast

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieFragment : Fragment() {

    private lateinit var binding: MovieFragmentBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MovieFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = (activity as MovieActivity).viewModel
            setLifecycleOwner(this@MovieFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupMovieAdapter()
        setupRecyclerView()
        setupToast()
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.start()
    }

    private fun setupMovieAdapter() {
        binding.viewModel?.let {
            movieAdapter = MovieAdapter(ArrayList(), it)
            return
        }

        Log.w(TAG, "ViewModel not initialized when attempting to set up adapter.")
    }

    private fun setupRecyclerView() {
        binding.rvMovieList.apply {
            adapter = movieAdapter
            setHasFixedSize(true)
            setPullRefreshEnabled(true)
            setLoadingMoreEnabled(true)
            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onLoadMore() {
                    binding.viewModel?.loadMore()
                }

                override fun onRefresh() {
                    binding.viewModel?.refresh()
                }
            })
        }
    }

    private fun setupToast() {
        binding.viewModel?.let {
            view?.setupToast(this, it.toastMessage)
        }
    }

    companion object {
        private const val TAG = "MovieFragment"
        fun newInstance() = MovieFragment()
    }
}