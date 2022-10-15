package com.example.fundamentalsubmission.data.datasources.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.utilities.ResultState

@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * from user WHERE username=:username")
    fun getFavoriteUserWithUsername(username: String): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE from user WHERE username=:username")
    suspend fun deleteUser(username: String)
}
