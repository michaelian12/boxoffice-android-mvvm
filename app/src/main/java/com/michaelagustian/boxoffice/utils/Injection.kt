package com.michaelagustian.boxoffice.utils

import android.content.Context
import com.michaelagustian.boxoffice.data.repository.movie.MovieRepository
import com.michaelagustian.boxoffice.data.source.local.AppDatabase
import com.michaelagustian.boxoffice.data.source.local.movie.MovieLocalDataSource
import com.michaelagustian.boxoffice.data.source.remote.MovieRemoteDataSource

/**
 * Created by Michael Agustian on 31/10/18.
 */

object Injection {

    fun provideMovieRepository(context: Context): MovieRepository {
        val localDatabase = AppDatabase.getInstance(context)

        return MovieRepository.getInstance(MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance(AppExecutors(), localDatabase.movieDao()))
    }
}