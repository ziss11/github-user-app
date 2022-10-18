package com.example.fundamentalsubmission.data.datasources

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.fundamentalsubmission.Injection.provideUserDao
import com.example.fundamentalsubmission.data.datasources.database.UserDao
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.utilities.ResultState

interface UserLocalDataSources {
    fun getFavoriteUsers(): LiveData<ResultState<List<UserEntity>>>
    suspend fun insertUser(user: UserEntity)
    suspend fun deleteUser(username: String)
    fun checkFavorite(username: String): LiveData<Boolean>
}

class UserLocalDataSourcesImpl private constructor(private val userDao: UserDao) :
    UserLocalDataSources {
    override fun getFavoriteUsers(): LiveData<ResultState<List<UserEntity>>> = liveData {
        emit(ResultState.Loading)

        try {
            val response = userDao.getFavoriteUsers()
            val userList: LiveData<ResultState<List<UserEntity>>> =
                response.map { ResultState.Success(it) }

            emitSource(userList)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun deleteUser(username: String) {
        userDao.deleteUser(username)
    }

    override fun checkFavorite(username: String): LiveData<Boolean> = liveData {
        val response = userDao.isFavorite(username)
        emitSource(response)
    }

    companion object {
        private var TAG = UserLocalDataSourcesImpl::class.java.simpleName

        private var instance: UserLocalDataSourcesImpl? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: UserLocalDataSourcesImpl(
                    provideUserDao(context)
                )
            }.also { instance = it }
    }
}