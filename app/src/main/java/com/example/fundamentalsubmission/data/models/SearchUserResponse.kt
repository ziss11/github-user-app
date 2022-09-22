package com.example.fundamentalsubmission.data.models

import com.google.gson.annotations.SerializedName

data class SearchUserResponse (
    @field: SerializedName("total_count") val totalCount: Int = 0,

    @field:SerializedName("login") val incompleteResults: Boolean = false,

    @field:SerializedName("items") val items: List<UserModel>? = null,
)