package com.michaelagustian.boxoffice.data.repository.movie

import com.michaelagustian.boxoffice.data.model.Movie

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieRepository(
        private val movieRemoteDataSource: MovieDataSource,
        val movieLocalDataSource: MovieDataSource
) : MovieDataSource {

    override fun getMovies(page: Int, callback: MovieDataSource.LoadMoviesCallback) {
        //get data from remote data source first
        movieRemoteDataSource.getMovies(page, object : MovieDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                // save data to local data source when successful
                movieLocalDataSource.saveMovies(movies)
                callback.onMoviesLoaded(movies)
            }

            override fun onDataNotAvailable(page: Int, message: String?) {
                // load local data when remote data is not available
                movieLocalDataSource.getMovies(page, object : MovieDataSource.LoadMoviesCallback {
                    override fun onMoviesLoaded(movies: List<Movie>) {
                        callback.onMoviesLoaded(movies)
                    }

                    override fun onDataNotAvailable(page: Int, message: String?) {
                        callback.onDataNotAvailable(page, message)
                    }
                })
            }
        })
    }

    override fun getMovie(movieId: Int, callback: MovieDataSource.GetMovieCallback) {
        //get data from remote data source first
        movieRemoteDataSource.getMovie(movieId, object : MovieDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                // save data to local data source when successful
                movieLocalDataSource.saveMovie(movie)
                callback.onMovieLoaded(movie)
            }

            override fun onDataNotAvailable(message: String?) {
                // load local data when remote data is not available
                movieLocalDataSource.getMovie(movieId, object : MovieDataSource.GetMovieCallback {
                    override fun onMovieLoaded(movie: Movie) {
                        callback.onMovieLoaded(movie)
                    }

                    override fun onDataNotAvailable(message: String?) {
                        callback.onDataNotAvailable(message)
                    }
                })
            }
        })
    }

    override fun saveMovies(movies: List<Movie>) {
        movieLocalDataSource.saveMovies(movies)
    }

    override fun saveMovie(movie: Movie) {
        movieLocalDataSource.saveMovie(movie)
    }

    companion object {
        private var INSTANCE: MovieRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param movieRemoteDataSource the backend data source
         * *
         * @param movieLocalDataSource  the device storage data source
         * *
         * @return the [MovieRepository] instance
         */
        @JvmStatic
        fun getInstance(movieRemoteDataSource: MovieDataSource, movieLocalDataSource: MovieDataSource) =
                INSTANCE ?: synchronized(MovieRepository::class.java) {
                    INSTANCE ?: MovieRepository(movieRemoteDataSource, movieLocalDataSource)
                            .also { INSTANCE = it }
                }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}