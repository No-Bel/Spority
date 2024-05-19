package com.example.spotify.adapter

import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.spotify.R
import com.example.spotify.data.entities.Song
import com.example.spotify.databinding.ListItemBinding
import javax.inject.Inject

class SongAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvPrimary = binding.tvPrimary
        val tvSecondary = binding.tvSecondary
        val ivItemImage = binding.ivItemImage
    }

    private val diffCallback = object: DiffUtil.ItemCallback<Song>() {

        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var songs: List<Song>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SongViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = songs[position]
        holder.apply {
            tvPrimary.text = curSong.title
            tvSecondary.text = curSong.subTitle
            glide.load(curSong.imageUrl).into(ivItemImage)

            setOnItemClickListener {
                onItemClickListener?.let { click ->
                    click(curSong)
                }
            }

            e("CUR_SONG", curSong.title)
        }

    }

    override fun getItemCount() = songs.size

    private var onItemClickListener: ((Song) -> Unit)? = null

    fun setOnItemClickListener(listener: (Song) -> Unit) {
        onItemClickListener = listener
    }
}