package com.example.spotify.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import com.example.spotify.R
import com.google.android.material.textview.MaterialTextView

class SwipeSongAdapter : BaseSongAdapter(R.layout.swipe_item) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curSong = songs[position]
        val tvPrimarySwipe = holder.itemView.requireViewById<MaterialTextView>(R.id.tvPrimarySwipe)
        val text = "${curSong.title} - ${curSong.subTitle}"
        tvPrimarySwipe.text = text


        setItemClickListener {
            onItemClickListener?.let { click ->
                click(curSong)
            }
        }
    }
}