package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.data.repositories.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(val userRepository: UserRepository) : ViewModel() {
    fun fetchFavoriteUsers() = userRepository.fetchFavoriteUsers()

    fun addToFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.add2Favorite(user)
        }
    }

    fun removeFromFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.removeFromFavorite(user)
        }
    }
}