package com.nafi.movflix.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nafi.movflix.R
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.databinding.FragmentHomeBinding
import com.nafi.movflix.databinding.SheetShareBinding
import com.nafi.movflix.databinding.SheetViewBinding
import com.nafi.movflix.presentation.home.adapter.MovieAdapter
import com.nafi.movflix.presentation.viewmore.ViewMoreActivity
import com.nafi.movflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private val nowPlayingAdapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            showBottomSheetDialog(movie)
        }
    }

    private val popularAdapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            showBottomSheetDialog(movie)
        }
    }

    private val topRatedAdapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            showBottomSheetDialog(movie)
        }
    }

    private val upComingAdapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            showBottomSheetDialog(movie)
        }
    }

    private var nowPlayingMovies: List<Movie> = emptyList()
    private var popularMovies: List<Movie> = emptyList()
    private var topRatedMovies: List<Movie> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupBanner()
        setupMovieNowPlayingList()
        setupMoviePopularList()
        setupMovieTopRatedList()
        setupMovieUpComingList()
        proceedMovieNowPlaying()
        proceedMoviePopular()
        proceedMovieTopRated()
        proceedMovieUpComing()
        combineAndSetBannerMovies()
        toViewMoreList()
        setClickListenerViewMoreAction()
    }

    private fun setClickListenerViewMoreAction() {
        val intent = Intent(requireContext(), ViewMoreActivity::class.java)

        binding.ivMoreTopRated.setOnClickListener {
            intent.putExtra(
                getString(R.string.text_header_intent),
                getString(R.string.text_intent_top_rated),
            )
            startActivity(intent)
        }
        binding.ivMorePopular.setOnClickListener {
            intent.putExtra(
                getString(R.string.text_header_intent),
                getString(R.string.text_intent_popular),
            )
            startActivity(intent)
        }
        binding.ivMoreUpcoming.setOnClickListener {
            intent.putExtra(
                getString(R.string.text_header_intent),
                getString(R.string.text_intent_upcoming),
            )
            startActivity(intent)
        }
        binding.ivMoreNowPlaying.setOnClickListener {
            intent.putExtra(
                getString(R.string.text_header_intent),
                getString(R.string.text_intent_now_playing),
            )
            startActivity(intent)
        }
    }

    private fun setupBanner() {
        binding.shimmerFrameLayoutBanner.isVisible = true
        binding.layoutBanner.ivBg.isVisible = false
        binding.layoutBanner.tvDesc.isVisible = false
        binding.layoutBanner.tvTitle.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        combineAndSetBannerMovies()
    }

    private fun setupMovieBanner(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            binding.shimmerFrameLayoutBanner.isVisible = false
            binding.layoutBanner.ivBg.isVisible = true
            binding.layoutBanner.tvDesc.isVisible = true
            binding.layoutBanner.tvTitle.isVisible = true
            val randomMovie = movies.random()
            bindBannerMovie(randomMovie)
            binding.layoutBanner.ibInfo.setOnClickListener {
                showInfoBottomSheet(randomMovie)
            }
            binding.layoutBanner.ibShare.setOnClickListener {
                showShareBottomSheet(randomMovie)
            }
        }
    }

    private fun bindBannerMovie(movie: Movie) {
        binding.layoutBanner.ivBg.load("${getString(R.string.text_link_image)}${movie.posterPath}")
        binding.layoutBanner.tvTitle.text = movie.title
        binding.layoutBanner.tvDesc.text = movie.desc
    }

    private fun setupMovieNowPlayingList() {
        binding.rvNowPlaying.apply {
            adapter = nowPlayingAdapter
        }
    }

    private fun setupMoviePopularList() {
        binding.rvPopular.apply {
            adapter = popularAdapter
        }
    }

    private fun setupMovieTopRatedList() {
        binding.rvTopRated.apply {
            adapter = topRatedAdapter
        }
    }

    private fun setupMovieUpComingList() {
        binding.rvUpcomingMovies.apply {
            adapter = upComingAdapter
        }
    }

    private fun proceedMovieNowPlaying() {
        homeViewModel.getMoviesNowPlaying().observe(viewLifecycleOwner) {
            it?.proceedWhen(
                doOnLoading = {
                    binding.movieNowPlayingShimmer.isVisible = true
                    binding.rvNowPlaying.isVisible = false
                    binding.shimmerFrameLayoutNowPlaying.startShimmer()
                    binding.layoutStateErrorNowPlaying.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.shimmerFrameLayoutNowPlaying.isVisible = false
                    binding.rvNowPlaying.isVisible = true
                    it.payload?.let { data ->
                        bindMovieNowPlayingList(data)
                        combineAndSetBannerMovies()
                    }
                    binding.layoutStateErrorNowPlaying.tvError.isVisible = false
                },
                doOnError = {
                    binding.movieNowPlayingShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorNowPlaying.tvError.isVisible = true
                    binding.layoutStateErrorNowPlaying.tvError.text = getString(R.string.text_failed_to_load_data)
                },
                doOnEmpty = {
                    binding.movieNowPlayingShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorNowPlaying.tvError.isVisible = false
                    binding.layoutStateErrorNowPlaying.tvError.text = getString(R.string.text_no_data)
                },
            )
        }
    }

    private fun proceedMoviePopular() {
        homeViewModel.getMoviesPopular().observe(viewLifecycleOwner) {
            it?.proceedWhen(
                doOnLoading = {
                    binding.moviePopularShimmer.isVisible = true
                    binding.rvPopular.isVisible = false
                    binding.shimmerFrameLayoutPopular.startShimmer()
                    binding.layoutStateErrorPopular.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.moviePopularShimmer.isVisible = false
                    binding.rvPopular.isVisible = true
                    it.payload?.let { data ->
                        bindMoviePopularList(data)
                        combineAndSetBannerMovies()
                    }
                    binding.layoutStateErrorPopular.tvError.isVisible = false
                },
                doOnError = {
                    binding.moviePopularShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorPopular.tvError.isVisible = true
                    binding.layoutStateErrorPopular.tvError.text = getString(R.string.text_failed_to_load_data)
                },
                doOnEmpty = {
                    binding.moviePopularShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorPopular.tvError.isVisible = false
                    binding.layoutStateErrorPopular.tvError.text = getString(R.string.text_no_data)
                },
            )
        }
    }

    private fun proceedMovieTopRated() {
        homeViewModel.getMoviesTopRated().observe(viewLifecycleOwner) {
            it?.proceedWhen(
                doOnLoading = {
                    binding.movieTopRatedShimmer.isVisible = true
                    binding.rvTopRated.isVisible = false
                    binding.shimmerFrameLayoutTopRated.startShimmer()
                    binding.layoutStateErrorTopRated.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.movieTopRatedShimmer.isVisible = false
                    binding.rvTopRated.isVisible = true
                    it.payload?.let { data ->
                        bindMovieTopRatedList(data)
                        combineAndSetBannerMovies()
                    }
                    binding.layoutStateErrorTopRated.tvError.isVisible = false
                },
                doOnError = {
                    binding.movieTopRatedShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorTopRated.tvError.isVisible = true
                    binding.layoutStateErrorTopRated.tvError.text = getString(R.string.text_failed_to_load_data)
                },
                doOnEmpty = {
                    binding.movieTopRatedShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorTopRated.tvError.isVisible = false
                    binding.layoutStateErrorTopRated.tvError.text = getString(R.string.text_no_data)
                },
            )
        }
    }

    private fun proceedMovieUpComing() {
        homeViewModel.getMoviesUpComing().observe(viewLifecycleOwner) {
            it?.proceedWhen(
                doOnLoading = {
                    binding.movieUpComingShimmer.isVisible = true
                    binding.rvUpcomingMovies.isVisible = false
                    binding.shimmerFrameLayoutUpComing.startShimmer()
                    binding.layoutStateErrorUpComing.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.movieUpComingShimmer.isVisible = false
                    binding.rvUpcomingMovies.isVisible = true
                    it.payload?.let { data ->
                        bindMovieUpComingList(data)
                    }
                    binding.layoutStateErrorUpComing.tvError.isVisible = false
                },
                doOnError = {
                    binding.movieUpComingShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorUpComing.tvError.isVisible = true
                    binding.layoutStateErrorUpComing.tvError.text = getString(R.string.text_failed_to_load_data)
                },
                doOnEmpty = {
                    binding.movieUpComingShimmer.isVisible = false
                    binding.rvNowPlaying.isVisible = false
                    binding.layoutStateErrorUpComing.tvError.isVisible = false
                    binding.layoutStateErrorUpComing.tvError.text = getString(R.string.text_no_data)
                },
            )
        }
    }

    private fun bindMovieNowPlayingList(data: List<Movie>) {
        nowPlayingMovies = data
        nowPlayingAdapter.submitData(data)
    }

    private fun bindMoviePopularList(data: List<Movie>) {
        popularMovies = data
        popularAdapter.submitData(data)
    }

    private fun bindMovieTopRatedList(data: List<Movie>) {
        topRatedMovies = data
        topRatedAdapter.submitData(data)
    }

    private fun bindMovieUpComingList(data: List<Movie>) {
        upComingAdapter.submitData(data)
    }

    private fun combineAndSetBannerMovies() {
        val combinedMovies = nowPlayingMovies + popularMovies + topRatedMovies
        if (combinedMovies.isNotEmpty()) {
            setupMovieBanner(combinedMovies)
        }
    }

    private fun showBottomSheetDialog(movie: Movie) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = SheetViewBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            ivBannerFilm.load("${getString(R.string.text_link_image)}${movie.backdropPath}")
            ivPoster.load("${getString(R.string.text_link_image)}${movie.posterPath}")
            tvTitleFilm.text = movie.title
            tvDescFilm.text = movie.desc
            tvRelease.text = movie.releaseDate
            tvRating.text = movie.voteAverage.toString()
        }

        bottomSheetBinding.btnShared.setOnClickListener {
            bottomSheetDialog.dismiss()
            showBottomSheetShare(movie)
        }

        checkMovieIsList(movie, bottomSheetBinding)

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun showBottomSheetShare(movie: Movie) {
        val shareBottomSheetDialog = BottomSheetDialog(requireContext())
        val shareBottomSheetBinding = SheetShareBinding.inflate(layoutInflater)
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        shareBottomSheetBinding.apply {
            tvTitleFilm.text = movie.title
            tvUrlFilm.text = (getString(R.string.text_url_poster, movie.posterPath))
            btnCopyUrl.setOnClickListener {
                val clip = ClipData.newPlainText(getString(R.string.text_label_url), tvUrlFilm.text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), getString(R.string.text_url_incopy), Toast.LENGTH_SHORT).show()
            }
            btnQuickShare.setOnClickListener {
                val posterUrl = "${getString(R.string.text_link_image)}${movie.posterPath}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(posterUrl))
                startActivity(intent)
            }
        }
        shareBottomSheetDialog.setContentView(shareBottomSheetBinding.root)
        shareBottomSheetDialog.show()
    }

    private fun showInfoBottomSheet(movie: Movie) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = SheetViewBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            ivBannerFilm.load("${getString(R.string.text_link_image)}${movie.backdropPath}")
            ivPoster.load("${getString(R.string.text_link_image)}${movie.posterPath}")
            tvTitleFilm.text = movie.title
            tvDescFilm.text = movie.desc
            tvRelease.text = movie.releaseDate
            tvRating.text = movie.voteAverage.toString()
        }

        bottomSheetBinding.btnShared.setOnClickListener {
            bottomSheetDialog.dismiss()
            showBottomSheetShare(movie)
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun showShareBottomSheet(movie: Movie) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = SheetShareBinding.inflate(layoutInflater)
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        bottomSheetBinding.apply {
            tvTitleFilm.text = movie.title
            tvUrlFilm.text = (getString(R.string.text_url_poster, movie.posterPath))
            btnCopyUrl.setOnClickListener {
                val clip = ClipData.newPlainText(getString(R.string.text_label_url), tvUrlFilm.text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), getString(R.string.text_url_incopy), Toast.LENGTH_SHORT).show()
            }
            btnQuickShare.setOnClickListener {
                val posterUrl = "${getString(R.string.text_link_image)}${movie.posterPath}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(posterUrl))
                startActivity(intent)
            }
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun toViewMoreList() {
        binding.ivMoreTopRated.setOnClickListener {
            startActivity(Intent(requireContext(), ViewMoreActivity::class.java))
        }
    }

    private fun setClickAddList(
        detail: Movie,
        bottomSheetBinding: SheetViewBinding,
    ) {
        bottomSheetBinding.btnList.setOnClickListener {
            addToList(detail)
        }
    }

    private fun setClickRemoveList(
        movieId: Int?,
        bottomSheetBinding: SheetViewBinding,
    ) {
        bottomSheetBinding.btnList.setOnClickListener {
            removeFromList(movieId)
        }
    }

    private fun checkMovieIsList(
        data: Movie,
        bottomSheetBinding: SheetViewBinding,
    ) {
        homeViewModel.checkMovieList(data.id).observe(
            viewLifecycleOwner,
        ) { isList ->
            if (isList.isEmpty()) {
                bottomSheetBinding.btnList.setIconResource(R.drawable.ic_add)
                setClickAddList(data, bottomSheetBinding)
            } else {
                bottomSheetBinding.btnList.setIconResource(R.drawable.ic_plus)
                setClickRemoveList(data.id, bottomSheetBinding)
            }
        }
    }

    private fun removeFromList(movieId: Int?) {
        homeViewModel.removeFromList(movieId).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_done_delete_to_list),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_failed_delete_to_list),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun addToList(detail: Movie) {
        homeViewModel.addToList(detail).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_done_add_to_list),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_failed_add_to_list),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }
}
