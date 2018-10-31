package com.michaelagustian.boxoffice.features.movie

import com.michaelagustian.boxoffice.data.model.Movie

/**
 * Created by Michael Agustian on 31/10/18.
 */

interface MovieItemActionListener {
    fun onMovieDetailClicked(movie: Movie)
}