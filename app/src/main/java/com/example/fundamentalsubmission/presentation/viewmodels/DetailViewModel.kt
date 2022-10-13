package com.example.fundamentalsubmission.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.datasources.service.ApiConfig
import com.example.fundamentalsubmission.data.repositories.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(val userRepository: UserRepository) : ViewModel() {
    fun getDetailUser(username: String) = userRepository.getUserDetail(username)

    fun fetchUserFollowers(username: String) = userRepository.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = userRepository.fetchUserFollowing(username)
}