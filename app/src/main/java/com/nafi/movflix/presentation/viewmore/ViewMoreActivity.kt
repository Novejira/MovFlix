package com.nafi.movflix.presentation.viewmore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nafi.movflix.databinding.ActivityViewMoreBinding
import com.nafi.movflix.presentation.viewmore.adapter.ViewMoreAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ViewMoreActivity : AppCompatActivity() {
    private val binding: ActivityViewMoreBinding by lazy {
        ActivityViewMoreBinding.inflate(layoutInflater)
    }

    private val viewMoreViewModel: ViewMoreViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private val viewMoreAdapter: ViewMoreAdapter by lazy {
        ViewMoreAdapter { movie ->
            Toast.makeText(this, "Clicked on ${movie.desc}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val HEADER = "HEADER"

        fun startActivity(
            context: Context,
            header: String,
        ) {
            val intent = Intent(context, ViewMoreActivity::class.java)
            intent.putExtra(HEADER, header)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupMovieTopRatedList()
        observeData()
        setClickListener()
    }

    private fun observeData() {
        viewMoreViewModel.header.let {
            binding.tvHeaderViewMore.text = it
            when (it) {
                "Top Rated" -> getTopRated()
                "Popular" -> getPopular()
                "Upcoming" -> getUpcoming()
                "Now Playing" -> getNowPlaying()
            }
        }
    }

    private fun setClickListener() {
        binding.ivArrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupMovieTopRatedList() {
        binding.rvMovieList.apply {
            adapter = viewMoreAdapter
        }
    }

    private fun getTopRated() {
        lifecycleScope.launch {
            viewMoreViewModel.topRatedMovies().collectLatest { pagingData ->
                viewMoreAdapter.submitData(pagingData)
            }
        }
    }

    private fun getUpcoming() {
        lifecycleScope.launch {
            viewMoreViewModel.upComingMovies().collectLatest { pagingData ->
                viewMoreAdapter.submitData(pagingData)
            }
        }
    }

    private fun getNowPlaying() {
        lifecycleScope.launch {
            viewMoreViewModel.nowPlayingMovies().collectLatest { pagingData ->
                viewMoreAdapter.submitData(pagingData)
            }
        }
    }

    private fun getPopular() {
        lifecycleScope.launch {
            viewMoreViewModel.popularMovies().collectLatest { pagingData ->
                viewMoreAdapter.submitData(pagingData)
            }
        }
    }
}
