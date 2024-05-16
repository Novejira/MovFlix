package com.nafi.movflix.presentation.mylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nafi.movflix.core.ViewHolderBinder
import com.nafi.movflix.data.model.ListMovie
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.databinding.ItemListGridBinding

interface ListMovieListener {
    fun onDeleteListClicked(list: Movie)

    fun onItemClicked(movie: Movie?)
}

class ListMovieAdapter(private val listListener: ListMovieListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<ListMovie>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (listListener != null) {
            FavoriteViewHolder(
                ItemListGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                listListener,
            )
        } else {
            FavoriteViewHolder(
                ItemListGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                listListener,
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Movie>).bind(dataDiffer.currentList[position])
    }
}

class FavoriteViewHolder(
    private val binding: ItemListGridBinding,
    private val listListener: ListMovieListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Movie> {
    override fun bind(data: Movie) {
        setFavoriteData(data)
        setClickListener(data)
    }

    private fun setFavoriteData(data: Movie) {
        with(binding) {
            ivMoviePhoto.load(data.posterPath) {
                crossfade(true)
            }
            tvMovieName.text = data.title
        }
    }

    private fun setClickListener(data: Movie) {
        with(binding) {
            itemView.setOnClickListener { listListener?.onItemClicked(data) }
        }
    }
}
