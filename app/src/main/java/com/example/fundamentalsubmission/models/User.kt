package com.example.fundamentalsubmission.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val name: String,
    val company: String,
    val photo: Int,
    val location: String,
    val followers: Int,
    val following: Int,
    val repositories: Int,
) : Parcelable