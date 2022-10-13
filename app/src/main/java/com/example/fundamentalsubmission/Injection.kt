package com.example.fundamentalsubmission

import com.example.fundamentalsubmission.data.datasources.RemoteDataSources
import com.example.fundamentalsubmission.data.datasources.service.ApiConfig
import com.example.fundamentalsubmission.data.datasources.service.ApiService
import com.example.fundamentalsubmission.data.repositories.UserRepository

object Injection {
    fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    fun provideRemoteDataSources(): RemoteDataSources {
        return RemoteDataSources.getInstance()
    }

    fun provideUserRepository(): UserRepository {
        return UserRepository.getInstance()
    }
}