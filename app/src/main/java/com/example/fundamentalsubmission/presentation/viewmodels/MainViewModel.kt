package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalsubmission.data.repositories.SettingRepository
import com.example.fundamentalsubmission.data.repositories.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository,
) : ViewModel() {
    fun fetchUsers() = userRepository.fetchUsers()

    fun fetchSearchedUsers(query: String) = userRepository.fetchSearchedUsers(query)

    fun getThemeSetting(): LiveData<Int> {
        return settingRepository.getThemeSetting()
    }

    fun saveThemeSetting(themeMode: Int) {
        viewModelScope.launch {
            settingRepository.saveThemeSetting(themeMode)
        }
    }
}