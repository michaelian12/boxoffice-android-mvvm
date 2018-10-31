package com.michaelagustian.boxoffice.data.model

import android.arch.persistence.room.*
import com.michaelagustian.boxoffice.BuildConfig

/**
 * Created by Michael Agustian on 31/10/18.
 */

@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Int? = 0,

        @ColumnInfo(name = "vote_average")
        var vote_average: Double? = 0.0,

        @ColumnInfo(name = "title")
        var title: String? = "",

        @ColumnInfo(name = "genre_ids")
        var genre_ids: List<Int>? = null,

        @ColumnInfo(name = "poster_path")
        var poster_path: String? = "",

        @ColumnInfo(name = "backdrop_path")
        var backdrop_path: String? = "",

        @ColumnInfo(name = "overview")
        var overview: String? = "",

        @ColumnInfo(name = "release_date")
        var release_date: String? = "",

        var page: Int? = 0
) {

    val posterUrl: String
        get() = BuildConfig.BASE_IMAGE_URL + poster_path

    val backdropUrl: String
        get() = BuildConfig.BASE_IMAGE_URL + backdrop_path
}