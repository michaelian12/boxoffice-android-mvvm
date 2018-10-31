package com.michaelagustian.boxoffice.features.moviedetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.michaelagustian.boxoffice.R
import com.michaelagustian.boxoffice.utils.Injection
import com.michaelagustian.boxoffice.utils.replaceFragmentInActivity

/**
 * Created by Michael Agustian on 31/10/18.
 */

class MovieDetailActivity : AppCompatActivity() {

    lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)
        setupFragment()
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.fl_movie_detail_container) ?:
        replaceFragmentInActivity(MovieDetailFragment.newInstance(
                intent.getIntExtra(EXTRA_MOVIE_ID, 0)), R.id.fl_movie_detail_container
        )
    }

    private fun setupViewModel() {
        viewModel = MovieDetailViewModel(application, Injection.provideMovieRepository(this))
    }

    companion object {
        const val EXTRA_MOVIE_ID = "MOVIE_ID"
    }
}