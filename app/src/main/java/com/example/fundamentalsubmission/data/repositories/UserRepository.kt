package com.example.fundamentalsubmission.data.repositories

import com.example.fundamentalsubmission.Injection.provideRemoteDataSources
import com.example.fundamentalsubmission.Injection.provideUserRepository
import com.example.fundamentalsubmission.data.datasources.RemoteDataSources

class UserRepository private constructor(private val remoteDataSources: RemoteDataSources) {
    fun fetchUsers() = remoteDataSources.getUsers()

    fun fetchSearchedUsers(query: String) = remoteDataSources.getSearchedUsers(query)

    fun getUserDetail(username: String) = remoteDataSources.getUserByUsername(username)

    fun fetchUserFollowers(username: String) = remoteDataSources.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = remoteDataSources.fetchUserFollowing(username)

    companion object {
        private var instance: UserRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: UserRepository(provideRemoteDataSources())
        }.also { instance = it }
    }
}