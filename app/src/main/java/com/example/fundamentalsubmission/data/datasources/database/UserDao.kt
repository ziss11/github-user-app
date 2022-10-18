package com.example.fundamentalsubmission.data.datasources.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundamentalsubmission.data.models.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * from user")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * from user WHERE username = :username)")
    fun isFavorite(username: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    @Query("DELETE from user WHERE username=:username")
    fun deleteUser(username: String)
}
