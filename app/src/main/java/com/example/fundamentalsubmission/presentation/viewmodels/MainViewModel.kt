package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.repositories.UserRepository

class MainViewModel(val userRepository: UserRepository) : ViewModel() {
    fun fetchUsers() = userRepository.fetchUsers()

    fun fetchSearchedUsers(query: String) = userRepository.fetchSearchedUsers(query)
}