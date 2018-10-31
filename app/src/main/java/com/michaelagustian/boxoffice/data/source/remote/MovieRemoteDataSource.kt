package com.michaelagustian.boxoffice.data.source.remote

import android.annotation.SuppressLint
import com.michaelagustian.boxoffice.data.model.Movie
import com.michaelagustian.boxoffice.data.repository.movie.MovieDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Michael Agustian on 31/10/18.
 */

@SuppressLint("CheckResult")
class MovieRemoteDataSource : MovieDataSource {

    private val apiService = ApiService.create()

    override fun getMovies(page: Int, callback: MovieDataSource.LoadMoviesCallback) {
        apiService.getMovieDiscover(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.results == null || it.results!!.isEmpty())
                        callback.onDataNotAvailable(page)
                    else
                        callback.onMoviesLoaded(it.results!!)
                }, { throwable ->
                    try {
                        callback.onDataNotAvailable(page, throwable.message)
                    } catch (e: Exception) {
                        callback.onDataNotAvailable(page, "An error occurred")
                    }
                })
    }

    override fun getMovie(movieId: Int, callback: MovieDataSource.GetMovieCallback) {
        apiService.getMovieDetail(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it == null)
                        callback.onDataNotAvailable()
                    else
                        callback.onMovieLoaded(it)
                }, { throwable ->
                    try {
                        callback.onDataNotAvailable(throwable.message)
                    } catch (e: Exception) {
                        callback.onDataNotAvailable("An error occurred")
                    }
                })
    }

    override fun saveMovies(movies: List<Movie>) {}

    override fun saveMovie(movie: Movie) {}

    companion object {
        private var INSTANCE: MovieRemoteDataSource? = null

        @JvmStatic
        fun getInstance(): MovieRemoteDataSource =
                INSTANCE ?: synchronized(MovieRemoteDataSource::class.java) {
                    INSTANCE ?: MovieRemoteDataSource()
                            .also { INSTANCE = it }
                }
    }
}