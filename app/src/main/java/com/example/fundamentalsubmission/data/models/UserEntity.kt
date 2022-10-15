package com.example.fundamentalsubmission.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val avatar: String,
)
