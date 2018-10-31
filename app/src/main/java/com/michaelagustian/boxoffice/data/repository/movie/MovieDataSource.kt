package com.michaelagustian.boxoffice.data.repository.movie

import com.michaelagustian.boxoffice.data.model.Movie

/**
 * Created by Michael Agustian on 31/10/18.
 */

interface MovieDataSource {

    interface LoadMoviesCallback {
        fun onMoviesLoaded(movies: List<Movie>)
        fun onDataNotAvailable(page: Int, message: String? = null)
    }

    interface GetMovieCallback {
        fun onMovieLoaded(movie: Movie)
        fun onDataNotAvailable(message: String? = null)
    }

    fun getMovies(page: Int, callback: LoadMoviesCallback)
    fun getMovie(movieId: Int, callback: GetMovieCallback)
    fun saveMovies(movies: List<Movie>)
    fun saveMovie(movie: Movie)
}