package com.example.fundamentalsubmission.data.datasources.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.utilities.ResultState

@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(id: String)
}
