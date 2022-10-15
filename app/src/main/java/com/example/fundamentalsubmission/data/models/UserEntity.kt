package com.example.fundamentalsubmission.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Int = 0,
    val username: String? = null,
    val avatar: String? = null,
)
