package com.example.fundamentalsubmission.presentation.viewmodels.dummy_data

import androidx.lifecycle.MutableLiveData
import com.example.fundamentalsubmission.data.models.UserEntity
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.utilities.ResultState

object DummyObject {
    val dummyUserModel = UserModel(
        id = 0,
        username = "username",
        name = "name",
        avatar = "avatar",
        company = "company",
        location = "location",
        bio = "bio",
        followers = 0,
        following = 0,
        publicRepos = 0,
    )
    private val dummyUserEntity = UserEntity(
        id = 0,
        username = "username",
        avatar = "avatar",
    )
    private val dummyResultListUserModel = ResultState.Success(
        listOf(dummyUserModel)
    )
    private val dummyResultListUserEntity = ResultState.Success(
        listOf(dummyUserEntity)
    )
    val dummyIsFavoriteUser = MutableLiveData<Boolean>().apply {
        value = true
    }
    val dummyResultUserModel = MutableLiveData<ResultState<UserModel>>().apply {
        value = ResultState.Success(dummyUserModel)
    }
    val dummyListUserModel = MutableLiveData<ResultState<List<UserModel>>>().apply {
        value = dummyResultListUserModel
    }
    val dummyListUserEntity = MutableLiveData<ResultState<List<UserEntity>>>().apply {
        value = dummyResultListUserEntity
    }
    val dummyThemeMode = MutableLiveData<Int>().apply {
        value = 0
    }

}