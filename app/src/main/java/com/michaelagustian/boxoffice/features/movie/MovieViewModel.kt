package com.michaelagustian.boxoffice.features.movie

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.michaelagustian.boxoffice.SingleLiveEvent
import com.michaelagustian.boxoffice.data.model.Movie
import com.michaelagustian.boxoffice.data.repository.movie.MovieDataSource
import com.michaelagustian.boxoffice.data.repository.movie.MovieRepository

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieViewModel(
        context: Application,
        private val movieRepository: MovieRepository
) : AndroidViewModel(context) {

    private var page = 1
    private var mMovies = ArrayList<Movie>()
    internal val openMovieDetailEvent = SingleLiveEvent<Movie>()

    // These observable fields will update Views automatically
    var items = MutableLiveData<List<Movie>>()
    var loadingView = MutableLiveData<Boolean>()
    var recyclerView = MutableLiveData<Boolean>()
    var emptyView = MutableLiveData<Boolean>()
    var refreshComplete = MutableLiveData<Boolean>()
    var loadMoreComplete = MutableLiveData<Boolean>()
    var toastMessage = SingleLiveEvent<String>()

    fun start() {
        recyclerView.value = false
        emptyView.value = false
        loadingView.value = true
        getMovies(page)
    }

    fun refresh() {
        page = 1
        getMovies(page)
        refreshComplete.value = false
    }

    fun loadMore() {
        page++
        getMovies(page)
        loadMoreComplete.value = false
    }

    private fun getMovies(page: Int) {
        movieRepository.getMovies(page, object : MovieDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                if (page == 1) mMovies.clear()

                mMovies.addAll(movies)
                items.postValue(mMovies)
                recyclerView.value = true
                emptyView.value = false
                loadingView.value = false
                refreshComplete.value = true
                loadMoreComplete.value = true
            }

            override fun onDataNotAvailable(page: Int, message: String?) {
                if (page == 1) {
                    recyclerView.value = false
                    emptyView.value = true
                }

                loadingView.value = false
                refreshComplete.value = true
                loadMoreComplete.value = true

                message?.let {
                    showMessage(it)
                }
            }
        })
    }

    private fun showMessage(message: String) {
        toastMessage.value = message
    }
}