package com.example.fundamentalsubmission.data.repositories

import android.content.Context
import com.example.fundamentalsubmission.Injection.provideLocalDataSources
import com.example.fundamentalsubmission.Injection.provideRemoteDataSources
import com.example.fundamentalsubmission.data.datasources.UserLocalDataSources
import com.example.fundamentalsubmission.data.datasources.UserRemoteDataSources
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.utilities.toEntity

class UserRepository private constructor(
    private val remoteDataSources: UserRemoteDataSources,
    private val localDataSources: UserLocalDataSources
) {
    fun fetchUsers() = remoteDataSources.getUsers()

    fun fetchSearchedUsers(query: String) = remoteDataSources.getSearchedUsers(query)

    fun getUserDetail(username: String) =
        remoteDataSources.getUserByUsername(username)

    fun fetchUserFollowers(username: String) = remoteDataSources.fetchUserFollowers(username)

    fun fetchUserFollowing(username: String) = remoteDataSources.fetchUserFollowing(username)

    fun fetchFavoriteUsers() = localDataSources.getFavoriteUsers()

    fun checkFavorite(username: String) = localDataSources.checkFavorite(username)

    fun add2Favorite(user: UserModel) = localDataSources.insertUser(user.toEntity())

    fun removeFromFavorite(username: String) = localDataSources.deleteUser(username)

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: UserRepository(provideRemoteDataSources(), provideLocalDataSources(context))
        }.also { instance = it }
    }
}