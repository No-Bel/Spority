package com.example.spotify.ui

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.RequestManager
import com.example.spotify.R
import com.example.spotify.adapter.SwipeSongAdapter
import com.example.spotify.data.entities.Song
import com.example.spotify.databinding.ActivityMainBinding
import com.example.spotify.ui.viewmodel.MainViewModel
import com.example.spotify.util.Status.ERROR
import com.example.spotify.util.Status.LOADING
import com.example.spotify.util.Status.SUCCESS
import com.example.spotify.util.extensions.hide
import com.example.spotify.util.extensions.isPlaying
import com.example.spotify.util.extensions.show
import com.example.spotify.util.extensions.toSong
import com.example.spotify.util.extensions.visibility
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var swipeSongAdapter: SwipeSongAdapter

    @Inject
    lateinit var glide: RequestManager

    private var curPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        subscribeToObservers()
        setListeners()
        setupPager()
    }

    private fun setupPager() {
        binding.vpSong.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (playbackState?.isPlaying == true) {
                    mainViewModel.playOrToggleSong(swipeSongAdapter.songs[position])
                } else curPlayingSong = swipeSongAdapter.songs[position]
            }
        })
    }

    private fun setListeners() {
        binding.ivPlayPause.setOnClickListener {
            curPlayingSong?.let {
                mainViewModel.playOrToggleSong(it, true)
            }
        }

        swipeSongAdapter.setItemClickListener {
            binding.navHostFragment.findNavController().navigate(
                R.id.globalActionToSongFragment
            )
        }

        binding.navHostFragment.findNavController().addOnDestinationChangedListener { _, desdination, _ ->
            when(desdination.id) {
                R.id.songFragment -> {
                    bottomBarVisibility(false)
                }
                R.id.homeFragment -> {
                    bottomBarVisibility(true)
                }
                else -> bottomBarVisibility(true)
            }

        }
    }

    private fun init() {
        binding.vpSong.adapter = swipeSongAdapter
    }

    private fun bottomBarVisibility(isVisible: Boolean) {
        binding.ivCurSongImage.visibility(isVisible)
        binding.vpSong.visibility(isVisible)
        binding.ivPlayPause.visibility(isVisible)
    }

    private fun switchViewPagerToCurrentSong(song: Song) {
        val newItemIndex = swipeSongAdapter.songs.indexOf(song)
        if (newItemIndex != -1) {
            binding.vpSong.currentItem = newItemIndex
            curPlayingSong = song
        }
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(this) {
            it?.let { result ->
                when (result.status) {
                    SUCCESS -> {
                        result.data?.let { songs ->
                            swipeSongAdapter.songs = songs
                            if (songs.isNotEmpty()) {
                                glide.load((curPlayingSong ?: songs[0]).imageUrl)
                                    .into(binding.ivCurSongImage)
                            }
                            switchViewPagerToCurrentSong(curPlayingSong ?: return@observe)
                        }
                    }

                    ERROR -> Unit
                    LOADING -> Unit
                }
            }
        }

        mainViewModel.curPlayingSong.observe(this) {
            if (it == null) return@observe

            curPlayingSong = it.toSong()
            glide.load(curPlayingSong?.imageUrl).into(binding.ivCurSongImage)
            switchViewPagerToCurrentSong(curPlayingSong ?: return@observe)
        }

        mainViewModel.playbackState.observe(this) {
            playbackState = it
            binding.ivPlayPause.setImageResource(
                if (playbackState?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play
            )
        }

        mainViewModel.isConnected.observe(this) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> {
                        Snackbar.make(
                            binding.root,
                            result.message ?: "An unknown error occured",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }

        mainViewModel.networkError.observe(this) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    ERROR -> {
                        Snackbar.make(
                            binding.root,
                            result.message ?: "An unknown error occured",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }
}