package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.repositories.UserRepository

class DetailViewModel(val userRepository: UserRepository) : ViewModel() {
    fun getDetailUser(username: String) = userRepository.getUserDetail(username)

    fun fetchUserFollowers(username: String) = userRepository.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = userRepository.fetchUserFollowing(username)
}