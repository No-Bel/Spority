// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("di_hilt", "2.47")
        set("coroutines_version", "1.7.1")
        set("lifecycle_version", "2.7.0")
        set("navigation_version", "2.7.7")
        set("glide_version", "4.12.0")
        set("exoplayer_version", "2.19.1")
    }
}
plugins {
    id("com.android.application") version "8.3.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id ("com.google.dagger.hilt.android") version "2.47" apply false
}