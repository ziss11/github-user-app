package com.example.fundamentalsubmission.data.datasources.service

import androidx.lifecycle.LiveData
import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(): LiveData<List<UserModel>>

    @GET("search/users")
    fun searchUser(
        @Query("q") q: String
    ): LiveData<SearchUserResponse>

    @GET("users/{username}")
    fun getUserByUsername(
        @Path("username") username: String
    ): LiveData<UserModel>

    @GET("users/{username}/{type}")
    fun getUserFollow(
        @Path("username") username: String,
        @Path("type") type: String
    ): LiveData<List<UserModel>>
}