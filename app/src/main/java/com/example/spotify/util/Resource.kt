package com.example.spotify.util

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)
        fun <T> error(message: String?, data: T?) = Resource(Status.ERROR, null, message)
        fun <T> loading(data: T?) = Resource(Status.SUCCESS, data, null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}