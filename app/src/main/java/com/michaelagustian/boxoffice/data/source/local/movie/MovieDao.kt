package com.michaelagustian.boxoffice.data.source.local.movie

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.michaelagustian.boxoffice.data.model.Movie

/**
 * Created by Michael Agustian on 31/10/18.
 */

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE page = :page")
    fun getMovies(page: Int): List<Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)
}