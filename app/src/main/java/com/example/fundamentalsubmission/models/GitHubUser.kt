package com.example.fundamentalsubmission.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubUser(
    val username: String?,
    val name: String?,
    val company:String?,
    val photo:Int?,
    val location: String?,
    val followers: String?,
    val following: String?,
    val repositories: String?,

):Parcelable