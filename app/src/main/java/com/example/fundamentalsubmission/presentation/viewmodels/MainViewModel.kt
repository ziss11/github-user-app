package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.repositories.SettingRepository
import com.example.fundamentalsubmission.data.repositories.UserRepository

class MainViewModel(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository,
) : ViewModel() {
    fun fetchUsers() = userRepository.fetchUsers()

    fun fetchSearchedUsers(query: String) = userRepository.fetchSearchedUsers(query)

    fun getThemeSetting() = settingRepository.getThemeSetting()

    fun saveThemeSetting(themeMode: Int) = settingRepository.saveThemeSetting(themeMode)
}