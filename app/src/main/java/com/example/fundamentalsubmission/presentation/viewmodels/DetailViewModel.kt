package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.repositories.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDetailUser(username: String) = userRepository.getUserDetail(username)

    fun fetchUserFollowers(username: String) = userRepository.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = userRepository.fetchUserFollowing(username)

    fun isFavoriteUser(username: String) = userRepository.checkFavorite(username)

    fun addToFavorite(user: UserModel) = userRepository.add2Favorite(user)

    fun removeFromFavorite(username: String) = userRepository.removeFromFavorite(username)
}