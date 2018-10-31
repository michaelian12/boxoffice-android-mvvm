package com.michaelagustian.boxoffice.data.source.local.movie

import android.support.annotation.VisibleForTesting
import com.michaelagustian.boxoffice.data.model.Movie
import com.michaelagustian.boxoffice.data.repository.movie.MovieDataSource
import com.michaelagustian.boxoffice.utils.AppExecutors

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieLocalDataSource private constructor(
        val appExecutors: AppExecutors,
        val movieDao: MovieDao
) : MovieDataSource {

    override fun getMovies(page: Int, callback: MovieDataSource.LoadMoviesCallback) {
        appExecutors.diskIO.execute {
            val movies = movieDao.getMovies(page)

            appExecutors.mainThread.execute {
                if (movies.isEmpty())
                    callback.onDataNotAvailable(page, "Movie data not found.")
                else
                    callback.onMoviesLoaded(movies)
            }
        }
    }

    override fun getMovie(movieId: Int, callback: MovieDataSource.GetMovieCallback) {
        appExecutors.diskIO.execute {
            val movie = movieDao.getMovie(movieId)

            appExecutors.mainThread.execute {
                if (movie == null)
                    callback.onDataNotAvailable("Movie data not found.")
                else
                    callback.onMovieLoaded(movie)
            }
        }
    }

    override fun saveMovies(movies: List<Movie>) {
        appExecutors.diskIO.execute { movieDao.insertMovies(movies) }
    }

    override fun saveMovie(movie: Movie) {
        appExecutors.diskIO.execute { movieDao.insertMovie(movie) }
    }

    companion object {
        private var INSTANCE: MovieLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, movieDao: MovieDao): MovieLocalDataSource =
                INSTANCE ?: synchronized(MovieLocalDataSource::class.java) {
                    INSTANCE ?: MovieLocalDataSource(appExecutors, movieDao)
                            .also { INSTANCE = it }
                }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}