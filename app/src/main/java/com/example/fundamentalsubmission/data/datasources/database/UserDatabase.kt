package com.example.fundamentalsubmission.data.datasources.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fundamentalsubmission.data.models.UserEntity

@Database(entities = [UserEntity::class], version = 6)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            ).build()
        }.also { instance = it }
    }
}