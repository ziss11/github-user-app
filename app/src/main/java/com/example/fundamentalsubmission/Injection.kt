package com.example.fundamentalsubmission

import android.content.Context
import com.example.fundamentalsubmission.data.datasources.LocalDataSources
import com.example.fundamentalsubmission.data.datasources.LocalDataSourcesImpl
import com.example.fundamentalsubmission.data.datasources.RemoteDataSources
import com.example.fundamentalsubmission.data.datasources.RemoteDataSourcesImpl
import com.example.fundamentalsubmission.data.datasources.database.UserDao
import com.example.fundamentalsubmission.data.datasources.database.UserDatabase
import com.example.fundamentalsubmission.data.datasources.service.ApiConfig
import com.example.fundamentalsubmission.data.datasources.service.ApiService
import com.example.fundamentalsubmission.data.repositories.UserRepository

object Injection {
    fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    fun provideUserDao(context: Context): UserDao {
        val db = UserDatabase.getInstance(context)
        return db.userDao()
    }

    fun provideRemoteDataSources(): RemoteDataSources {
        return RemoteDataSourcesImpl.getInstance()
    }

    fun provideLocalDataSources(context: Context): LocalDataSources {
        return LocalDataSourcesImpl.getInstance(context)
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(context)
    }
}