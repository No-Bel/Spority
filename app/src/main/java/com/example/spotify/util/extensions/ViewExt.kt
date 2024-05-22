package com.example.spotify.util.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}

fun View.visibility(isVisible: Boolean) {
    this.isVisible = isVisible
}