package com.nafi.movflix.presentation.mylist

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
import com.nafi.movflix.databinding.FragmentMyListBinding
import com.nafi.movflix.databinding.SheetShareBinding
import com.nafi.movflix.databinding.SheetViewBinding
import com.nafi.movflix.presentation.mylist.adapter.ListMovieAdapter
import com.nafi.movflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {
    private lateinit var binding: FragmentMyListBinding

    private val viewModel: MyListViewModel by viewModel()

    private val adapter: ListMovieAdapter by lazy {
        ListMovieAdapter { movie ->
            showBottomSheetDialog(movie)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setList()
    }

    private fun setList() {
        binding.rvListmovie.adapter = this@MyListFragment.adapter
    }

    private fun observeData() {
        viewModel.getAllList().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutStateFavorite.root.isVisible = true
                    binding.layoutStateFavorite.pbLoading.isVisible = false
                    binding.myMovieShimmer.isVisible = true
                    binding.layoutStateFavorite.tvError.isVisible = false
                    binding.rvListmovie.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutStateFavorite.root.isVisible = false
                    binding.layoutStateFavorite.pbLoading.isVisible = false
                    binding.myMovieShimmer.isVisible = false
                    binding.layoutStateFavorite.tvError.isVisible = false
                    binding.rvListmovie.isVisible = true
                    result.payload?.let {
                        adapter.submitData(it)
                    }
                },
                doOnEmpty = {
                    binding.layoutStateFavorite.root.isVisible = true
                    binding.layoutStateFavorite.pbLoading.isVisible = false
                    binding.myMovieShimmer.isVisible = false
                    binding.layoutStateFavorite.tvError.isVisible = true
                    binding.layoutStateFavorite.tvError.text =
                        getString(R.string.text_empty_movie_list)
                    binding.rvListmovie.isVisible = false
                    result.payload?.let {
                        adapter.submitData(it)
                    }
                },
                doOnError = {
                    binding.layoutStateFavorite.root.isVisible = true
                    binding.layoutStateFavorite.pbLoading.isVisible = false
                    binding.myMovieShimmer.isVisible = false
                    binding.layoutStateFavorite.tvError.isVisible = true
                    binding.rvListmovie.isVisible = false
                },
            )
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
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        shareBottomSheetBinding.apply {
            tvTitleFilm.text = movie.title
            tvUrlFilm.text = (getString(R.string.text_url_poster, movie.posterPath))
            btnCopyUrl.setOnClickListener {
                val clip = ClipData.newPlainText(getString(R.string.text_label_url), tvUrlFilm.text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), R.string.text_url_incopy, Toast.LENGTH_SHORT).show()
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
        viewModel.checkMovieList(data.id).observe(
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
        viewModel.removeFromList(movieId).observe(viewLifecycleOwner) {
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
        viewModel.addToList(detail).observe(viewLifecycleOwner) {
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
