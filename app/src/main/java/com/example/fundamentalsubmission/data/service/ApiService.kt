package com.example.fundamentalsubmission.data.service

import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
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
    ): Call<UserModel>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<UserModel>
}