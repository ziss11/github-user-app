package com.example.fundamentalsubmission.data.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.fundamentalsubmission.Injection.provideApiService
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.datasources.service.ApiService
import com.example.fundamentalsubmission.utilities.ResultState

class RemoteDataSources private constructor(private val apiService: ApiService) {
    fun getUsers(): LiveData<ResultState<List<UserModel>>> = liveData {
        emit(ResultState.Loading)

        try {
            val response = apiService.getUsers()
            val userList: LiveData<ResultState<List<UserModel>>> =
                response.map { ResultState.Success(it) }

            emitSource(userList)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getSearchedUsers(query: String): LiveData<ResultState<List<UserModel>>> = liveData {
        emit(ResultState.Loading)

        try {
            val response = apiService.searchUser(query)
            val userList: LiveData<ResultState<List<UserModel>>> =
                response.map { ResultState.Success(it.items!!) }

            emitSource(userList)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getUserByUsername(username: String): LiveData<ResultState<UserModel>> = liveData {
        emit(ResultState.Loading)

        try {
            val response = apiService.getUserByUsername(username)
            val userList: LiveData<ResultState<UserModel>> =
                response.map { ResultState.Success(it) }

            emitSource(userList)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun fetchUserFollowers(username: String): LiveData<ResultState<List<UserModel>>> = liveData {
        emit(ResultState.Loading)

        try {
            val response = apiService.getUserFollow(username, FOLLOWERS)
            val userList: LiveData<ResultState<List<UserModel>>> =
                response.map { ResultState.Success(it) }

            emitSource(userList)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun fetchUserFollowing(username: String): LiveData<ResultState<List<UserModel>>> = liveData {
        emit(ResultState.Loading)

        try {
            val response = apiService.getUserFollow(username, FOLLOWING)
            val userList: LiveData<ResultState<List<UserModel>>> =
                response.map { ResultState.Success(it) }

            emitSource(userList)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            emit(ResultState.Error(e.message.toString()))
        }
    }

    companion object {
        private var TAG = this::class.java.simpleName

        private const val FOLLOWERS = "followers"
        private const val FOLLOWING = "following"

        private var instance: RemoteDataSources? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RemoteDataSources(provideApiService())
        }.also { instance = it }
    }
}