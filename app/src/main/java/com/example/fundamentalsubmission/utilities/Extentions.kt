package com.example.fundamentalsubmission.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.data.models.UserModel

fun ImageView.loadImage(url: Any?) {
    Glide.with(this.context).load(url).into(this)
}

fun UserEntity.toModel(): UserModel {
    return UserModel(id = this.id, username = this.username, avatar = this.avatar)
}

fun UserModel.toEntity(): UserEntity {
    return UserEntity(id = this.id, username = this.username, avatar = this.avatar)
}