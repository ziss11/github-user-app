package com.example.fundamentalsubmission

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.fundamentalsubmission.data.datasources.*
import com.example.fundamentalsubmission.data.datasources.database.UserDao
import com.example.fundamentalsubmission.data.datasources.database.UserDatabase
import com.example.fundamentalsubmission.data.datasources.preferences.SettingsPreference
import com.example.fundamentalsubmission.data.datasources.service.ApiConfig
import com.example.fundamentalsubmission.data.datasources.service.ApiService
import com.example.fundamentalsubmission.data.repositories.SettingRepository
import com.example.fundamentalsubmission.data.repositories.UserRepository

object Injection {
    fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    fun provideUserDao(context: Context): UserDao {
        val db = UserDatabase.getInstance(context)
        return db.userDao()
    }

    fun provideSettingPref(dataStore: DataStore<Preferences>): SettingsPreference {
        return SettingsPreference.getInstance(dataStore)
    }

    fun provideRemoteDataSources(): UserRemoteDataSources {
        return UserRemoteDataSourcesImpl.getInstance()
    }

    fun provideLocalDataSources(context: Context): UserLocalDataSources {
        return UserLocalDataSourcesImpl.getInstance(context)
    }

    fun provideSettingDataSources(dataStore: DataStore<Preferences>): SettingsDataSource {
        return SettingsDataSourceImpl.getInstance(dataStore)
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(context)
    }

    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingRepository {
        return SettingRepository.getInstance(dataStore)
    }
}