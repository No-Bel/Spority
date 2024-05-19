package com.example.spotify.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.AsyncListDiffer
import com.bumptech.glide.RequestManager
import com.example.spotify.R
import com.google.android.material.textview.MaterialTextView
import javax.inject.Inject

class SongAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseSongAdapter(R.layout.list_item) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = songs[position]
        val tvPrimary = holder.itemView.requireViewById<MaterialTextView>(R.id.tvPrimary)
        val tvSecondary = holder.itemView.requireViewById<MaterialTextView>(R.id.tvSecondary)
        val ivItemImage = holder.itemView.requireViewById<AppCompatImageView>(R.id.ivItemImage)

        tvPrimary.text = curSong.title
        tvSecondary.text = curSong.subTitle
        glide.load(curSong.imageUrl).into(ivItemImage)

        setItemClickListener {
            onItemClickListener?.let { click ->
                click(curSong)
            }
        }
    }
}