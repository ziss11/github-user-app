package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.repositories.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun fetchFavoriteUsers() = userRepository.fetchFavoriteUsers()
}