package com.example.fundamentalsubmission.data.datasources.service

import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<UserModel>>

    @GET("search/users")
    fun searchUser(
        @Query("q") q: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getUserByUsername(
        @Path("username") username: String
    ): Call<UserModel>

    @GET("users/{username}/{type}")
    fun getUserFollow(
        @Path("username") username: String,
        @Path("type") type: String
    ): Call<List<UserModel>>
}