package com.example.fundamentalsubmission.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fundamentalsubmission.Injection.provideUserRepository
import com.example.fundamentalsubmission.data.repositories.UserRepository

class ViewModelFactory private constructor(val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Unknown view model class: ${modelClass}")
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(provideUserRepository())
        }.also { instance = it }
    }
}