package com.michaelagustian.boxoffice.features.moviedetail

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

class MovieDetailViewModel(
        context: Application,
        private val movieRepository: MovieRepository
) : AndroidViewModel(context) {

    // These observable fields will update Views automatically
    var movieBackdrop = MutableLiveData<String>()
    var moviePoster = MutableLiveData<String>()
    var movieTitle = MutableLiveData<String>()
    var movieVoteAverage = MutableLiveData<Double>()
    var movieOverview = MutableLiveData<String>()
    var loadingView = MutableLiveData<Boolean>()
    var movieDetailView = MutableLiveData<Boolean>()
    var emptyView = MutableLiveData<Boolean>()
    var toastMessage = SingleLiveEvent<String>()

    fun start(movieId: Int?) {
        movieId?.let {
            movieDetailView.value = false
            emptyView.value = false
            loadingView.value = true
            getMovie(it)
        }
    }

    private fun getMovie(movieId: Int) {
        movieRepository.getMovie(movieId, object : MovieDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                movieBackdrop.value = movie.backdropUrl
                moviePoster.value = movie.posterUrl
                movieTitle.value = movie.title
                movieVoteAverage.value = movie.vote_average
                movieOverview.value = movie.overview

                movieDetailView.value = true
                emptyView.value = false
                loadingView.value = false
            }

            override fun onDataNotAvailable(message: String?) {
                movieDetailView.value = false
                emptyView.value = true
                loadingView.value = false

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