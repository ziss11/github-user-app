package com.example.fundamentalsubmission.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
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
                    _searchedUsers.value = responseBody?.items!!
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

    companion object {
        private const val TAG = "MainActivity"
    }
}