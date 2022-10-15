package com.example.fundamentalsubmission.data.datasources

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.fundamentalsubmission.Injection
import com.example.fundamentalsubmission.Injection.provideUserDao
import com.example.fundamentalsubmission.data.datasources.database.UserDao
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.utilities.ResultState

interface LocalDataSources {
    fun getFavoriteUsers(): LiveData<ResultState<List<UserEntity>>>
    suspend fun insertUser(user: UserEntity)
    suspend fun deleteUser(user: UserEntity)
}

class LocalDataSourcesImpl private constructor(val userDao: UserDao) : LocalDataSources {
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

    override suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    companion object {
        private var TAG = LocalDataSources::class.java.simpleName

        private var instance: LocalDataSourcesImpl? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: LocalDataSourcesImpl(provideUserDao(context))
        }.also { instance = it }
    }
}