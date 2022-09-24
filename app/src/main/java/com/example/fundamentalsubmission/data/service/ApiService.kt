package com.example.fundamentalsubmission.data.service

import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: Bearer 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")
    @GET("search/users")
    fun searchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<UserModel>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UserModel>>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<UserModel>>
}