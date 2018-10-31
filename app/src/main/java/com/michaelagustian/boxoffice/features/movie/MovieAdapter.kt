package com.michaelagustian.boxoffice.features.movie

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.michaelagustian.boxoffice.R
import com.michaelagustian.boxoffice.data.model.Movie
import com.michaelagustian.boxoffice.databinding.MovieItemBinding

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieAdapter(
        private var movies: List<Movie>,
        private val movieViewModel: MovieViewModel
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)

        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val actionListener = object : MovieItemActionListener {
            override fun onMovieDetailClicked(movie: Movie) {
                movieViewModel.openMovieDetailEvent.value = movie
            }
        }

        holder.bind(movies[position], actionListener)
    }

    fun replaceData(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, actionListener: MovieItemActionListener) {
        binding.movie = movie
        binding.actionListener = actionListener
        binding.executePendingBindings()
    }
}