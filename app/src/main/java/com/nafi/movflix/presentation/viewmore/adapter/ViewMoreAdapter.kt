package com.nafi.movflix.presentation.viewmore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.databinding.LayoutMovieBinding

class ViewMoreAdapter(private val itemClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, ViewMoreAdapter.ItemMovieViewHolder>(MOVIE_COMPARATOR) {
    companion object {
        private val MOVIE_COMPARATOR =
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemMovieViewHolder {
        val binding =
            LayoutMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ItemMovieViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemMovieViewHolder,
        position: Int,
    ) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bindView(movie)
        }
    }

    class ItemMovieViewHolder(
        private val binding: LayoutMovieBinding,
        val itemClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                binding.ivMovieImg.load("https://image.tmdb.org/t/p/w500${item.posterPath}") {
                    crossfade(true)
                }
                binding.tvTitle.text = item.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
