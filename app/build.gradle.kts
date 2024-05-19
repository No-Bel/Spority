plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.spotify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.spotify"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime:${rootProject.extra["lifecycle_version"]}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["coroutines_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines_version"]}")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_version"]}")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navigation_version"]}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navigation_version"]}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${rootProject.extra["glide_version"]}")
    kapt("com.github.bumptech.glide:compiler:${rootProject.extra["glide_version"]}")

    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.9.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["di_hilt"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["di_hilt"]}")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore:25.0.0")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    // Firebase Storage KTX
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")

    // Firebase Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // ExoPlayer
    api("com.google.android.exoplayer:exoplayer-core:${rootProject.extra["exoplayer_version"]}")
    api("com.google.android.exoplayer:exoplayer-ui:${rootProject.extra["exoplayer_version"]}")
    api("com.google.android.exoplayer:extension-mediasession:${rootProject.extra["exoplayer_version"]}")
}