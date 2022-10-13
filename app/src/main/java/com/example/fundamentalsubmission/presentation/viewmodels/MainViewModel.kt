package com.example.fundamentalsubmission.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.datasources.service.ApiConfig
import com.example.fundamentalsubmission.data.repositories.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val userRepository: UserRepository) : ViewModel() {
    init {
        fetchUsers()
    }

    fun fetchUsers() = userRepository.fetchUsers()

    fun fetchSearchedUsers(query: String) = userRepository.fetchSearchedUsers(query)
}