package com.example.fundamentalsubmission.data.repositories

import android.content.Context
import com.example.fundamentalsubmission.Injection.provideLocalDataSources
import com.example.fundamentalsubmission.Injection.provideRemoteDataSources
import com.example.fundamentalsubmission.data.datasources.LocalDataSources
import com.example.fundamentalsubmission.data.datasources.RemoteDataSources
import com.example.fundamentalsubmission.data.models.UserEntity

class UserRepository private constructor(
    private val remoteDataSources: RemoteDataSources,
    private val localDataSources: LocalDataSources
) {
    fun fetchUsers() = remoteDataSources.getUsers()

    fun fetchSearchedUsers(query: String) = remoteDataSources.getSearchedUsers(query)

    fun getUserDetail(username: String) = remoteDataSources.getUserByUsername(username)

    fun fetchUserFollowers(username: String) = remoteDataSources.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = remoteDataSources.fetchUserFollowing(username)

    fun fetchFavoriteUsers() = localDataSources.getFavoriteUsers()

    suspend fun add2Favorite(user: UserEntity) = localDataSources.insertUser(user)

    suspend fun removeFromFavorite(id: String) = localDataSources.deleteUser(id)

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: UserRepository(provideRemoteDataSources(), provideLocalDataSources(context))
        }.also { instance = it }
    }
}