package com.example.fundamentalsubmission.data.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @field: SerializedName("id")
    val id: String? = null,

    @field:SerializedName("login")
    val username: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("avatar_url")
    val avatar: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field: SerializedName("bio")
    val bio: String? = null,

    @field: SerializedName("followers")
    val followers: Int = 0,

    @field: SerializedName("following")
    val following: Int = 0,

    @field: SerializedName("public_repos")
    val publicRepos: Int = 0,
)