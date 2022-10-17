package com.example.fundamentalsubmission.presentation.viewmodels

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fundamentalsubmission.Injection.provideSettingsRepository
import com.example.fundamentalsubmission.Injection.provideUserRepository
import com.example.fundamentalsubmission.data.repositories.SettingRepository
import com.example.fundamentalsubmission.data.repositories.UserRepository

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository, settingRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Unknown view model class: $modelClass")
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context, dataStore: DataStore<Preferences>? = null) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    provideUserRepository(context),
                    provideSettingsRepository(dataStore!!)
                )
            }.also { instance = it }
    }
}