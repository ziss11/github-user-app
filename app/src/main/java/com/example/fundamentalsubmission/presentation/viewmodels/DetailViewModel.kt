package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.repositories.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(val userRepository: UserRepository) : ViewModel() {
    fun getDetailUser(username: String) = userRepository.getUserDetail(username)

    fun fetchUserFollowers(username: String) = userRepository.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = userRepository.fetchUserFollowing(username)

    fun isFavoriteUser(username: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val userList = userRepository.getFavoriteByUsername(username)
            result.postValue(userList.value?.isNotEmpty() ?: false)
        }

        return result
    }

    fun addToFavorite(user: UserModel) {
        viewModelScope.launch {
            userRepository.add2Favorite(user)
        }
    }

    fun removeFromFavorite(username: String) {
        viewModelScope.launch {
            userRepository.removeFromFavorite(username)
        }
    }
}