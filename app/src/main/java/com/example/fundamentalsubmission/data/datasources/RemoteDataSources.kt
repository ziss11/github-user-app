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
    private val usersResult = MediatorLiveData<ResultState<List<UserModel>>>()
    private val searchedUsersResult = MediatorLiveData<ResultState<List<UserModel>>>()
    private val userDetailResult = MediatorLiveData<ResultState<UserModel>>()
    private val followersResult = MediatorLiveData<ResultState<List<UserModel>>>()
    private val followingResult = MediatorLiveData<ResultState<List<UserModel>>>()

    fun getUsers(): LiveData<ResultState<List<UserModel>>> {
        usersResult.value = ResultState.Loading

        val client = apiService.getUsers()
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    usersResult.value = ResultState.Success(responseBody)
                } else {
                    usersResult.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                usersResult.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return usersResult
    }

    fun getSearchedUsers(query: String): LiveData<ResultState<List<UserModel>>> {
        searchedUsersResult.value = ResultState.Loading

        val client = apiService.searchUser(query)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    searchedUsersResult.value = ResultState.Success(responseBody.items!!)
                } else {
                    searchedUsersResult.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                searchedUsersResult.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return searchedUsersResult
    }

    fun getUserByUsername(username: String): LiveData<ResultState<UserModel>> {
        userDetailResult.value = ResultState.Loading

        val client = apiService.getUserByUsername(username)
        client.enqueue(object : Callback<UserModel> {
            override fun onResponse(
                call: Call<UserModel>,
                response: Response<UserModel>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    userDetailResult.value = ResultState.Success(responseBody)
                } else {
                    userDetailResult.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                userDetailResult.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return userDetailResult
    }

    fun fetchUserFollowers(username: String): LiveData<ResultState<List<UserModel>>> {
        followersResult.value = ResultState.Loading

        val client = apiService.getUserFollow(username, FOLLOWERS)
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    followersResult.value = ResultState.Success(responseBody)
                } else {
                    followersResult.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                followersResult.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return followersResult
    }

    fun fetchUserFollowing(username: String): LiveData<ResultState<List<UserModel>>> {
        followingResult.value = ResultState.Loading

        val client = apiService.getUserFollow(username, FOLLOWING)
        client.enqueue(object : Callback<List<UserModel>> {
            override fun onResponse(
                call: Call<List<UserModel>>,
                response: Response<List<UserModel>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    followingResult.value = ResultState.Success(responseBody)
                } else {
                    followingResult.value = ResultState.Error(response.message().toString())
                    Log.d(TAG, response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                followingResult.value = ResultState.Error(t.message.toString())
                Log.d(TAG, t.message.toString())
            }
        })

        return followingResult
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