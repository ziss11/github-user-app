package com.example.fundamentalsubmission.data.datasources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.fundamentalsubmission.Injection.provideApiService
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.data.datasources.service.ApiService
import com.example.fundamentalsubmission.data.models.SearchUserResponse
import com.example.fundamentalsubmission.utilities.ResultState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSources private constructor(private val apiService: ApiService) {
    private val resultList = MediatorLiveData<ResultState<List<UserModel>>>()
    private val resultObject = MediatorLiveData<ResultState<UserModel>>()

    fun getUsers(): LiveData<ResultState<List<UserModel>>> {
        resultList.value = ResultState.Loading

        val client = apiService.getUsers()
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    resultList.value = ResultState.Success(responseBody)
                } else {
                    resultList.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                resultList.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return resultList
    }

    fun getSearchedUsers(query: String): LiveData<ResultState<List<UserModel>>> {
        resultList.value = ResultState.Loading

        val client = apiService.searchUser(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    resultList.value = ResultState.Success(responseBody.items!!)
                } else {
                    resultList.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                resultList.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return resultList
    }

    fun getUserByUsername(username: String): LiveData<ResultState<UserModel>> {
        resultObject.value = ResultState.Loading

        val client = apiService.getUserByUsername(username)
        client.enqueue(object : Callback<UserModel> {
            override fun onResponse(
                call: Call<UserModel>,
                response: Response<UserModel>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    resultObject.value = ResultState.Success(responseBody)
                } else {
                    resultObject.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                resultObject.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return resultObject
    }

    fun fetchUserFollowers(username: String): LiveData<ResultState<List<UserModel>>> {
        resultList.value = ResultState.Loading

        val client = apiService.getUserFollow(username, FOLLOWERS)
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    resultList.value = ResultState.Success(responseBody)
                } else {
                    resultList.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                resultList.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return resultList
    }

    fun fetchUserFollowing(username: String): LiveData<ResultState<List<UserModel>>> {
        resultList.value = ResultState.Loading

        val client = apiService.getUserFollow(username, FOLLOWING)
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    resultList.value = ResultState.Success(responseBody)
                } else {
                    resultList.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                resultList.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return resultList
    }

    companion object {
        private var TAG = RemoteDataSources::class.java.simpleName
        private const val FOLLOWERS = "followers"
        private const val FOLLOWING = "following"

        private var instance: RemoteDataSources? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RemoteDataSources(provideApiService())
        }.also { instance = it }
    }
}