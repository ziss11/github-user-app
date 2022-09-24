package com.example.fundamentalsubmission.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> = _user

    private val _followers = MutableLiveData<List<UserModel>?>()
    val followers: LiveData<List<UserModel>?> = _followers

    private val _followings = MutableLiveData<List<UserModel>?>()
    val followings: LiveData<List<UserModel>?> = _followings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUserByUsername(username)
        client.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                _isLoading.value = false

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _user.value = responseBody
                } else {
                    Log.e(TAG, "responseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "responseFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserFollow(username, "followers")
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _followers.value = responseBody
                } else {
                    Log.e(TAG, "responseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "responseFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowings(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserFollow(username, "following")
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _followings.value = responseBody
                } else {
                    Log.e(TAG, "responseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "responseFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}