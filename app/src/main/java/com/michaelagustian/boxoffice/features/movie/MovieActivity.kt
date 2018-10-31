package com.michaelagustian.boxoffice.features.movie

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.michaelagustian.boxoffice.R
import com.michaelagustian.boxoffice.features.moviedetail.MovieDetailActivity
import com.michaelagustian.boxoffice.utils.Injection
import com.michaelagustian.boxoffice.utils.replaceFragmentInActivity
import com.michaelagustian.boxoffice.utils.setupActionBar
import kotlinx.android.synthetic.main.toolbar.*

class MovieActivity : AppCompatActivity(), MovieItemNavigator {

    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity)
        setupActionBar(toolbar)
        setupFragment()
        setupViewModel()
    }

    override fun openMovieDetail(movieId: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieId)
        }

        startActivity(intent)
    }

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.fl_movie_container) ?:
        replaceFragmentInActivity(MovieFragment.newInstance(), R.id.fl_movie_container)
    }

    private fun setupViewModel() {
        viewModel = MovieViewModel(application, Injection.provideMovieRepository(this)).apply {
            openMovieDetailEvent.observe(this@MovieActivity, Observer {
                openMovieDetail(it?.id!!)
            })
        }

//        viewModel = obtainViewModel().apply {
//            openMovieDetailEvent.observe(this@MovieActivity, Observer {
//                openMovieDetail(it?.id!!)
//            })
//        }
    }

//    fun obtainViewModel(): MovieViewModel = obtainViewModel(MovieViewModel::class.java)
}