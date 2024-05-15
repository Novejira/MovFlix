package com.nafi.movflix.presentation.viewmore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nafi.movflix.databinding.ActivityViewMoreBinding
import com.nafi.movflix.presentation.viewmore.adapter.ViewMoreAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewMoreActivity : AppCompatActivity() {
    private val binding: ActivityViewMoreBinding by lazy {
        ActivityViewMoreBinding.inflate(layoutInflater)
    }

    private val viewMoreViewModel: ViewMoreViewModel by viewModel()

    private val topRatedAdapter: ViewMoreAdapter by lazy {
        ViewMoreAdapter { movie ->
            Toast.makeText(this, "Clicked on ${movie.desc}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupMovieTopRatedList()
        getTopRated()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivArrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupMovieTopRatedList() {
        binding.rvMovieList.apply {
            adapter = topRatedAdapter
        }
    }

    private fun getTopRated() {
        lifecycleScope.launch {
            viewMoreViewModel.topRatedMovies().collectLatest { pagingData ->
                topRatedAdapter.submitData(pagingData)
            }
        }
    }
}
