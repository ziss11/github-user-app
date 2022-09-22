package com.example.fundamentalsubmission.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val _userDetail = MutableLiveData<UserModel>()
    val userDetail: LiveData<UserModel> = _userDetail

    private val _searchedUsers = MutableLiveData<List<UserModel>>()
    val searchedUsers: LiveData<List<UserModel>> = _searchedUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                if (response.isSuccessful) {
                    _searchedUsers.value = responseBody?.items
                } else {
                    Log.e(TAG, "responseFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                _isLoading.value = false

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _userDetail.value = responseBody
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
}