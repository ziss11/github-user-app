package com.example.fundamentalsubmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.databinding.ActivityMainBinding
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.presentation.ui.activity.DetailActivity
import com.example.fundamentalsubmission.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.search_users)

        subscribe()

        val layout = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layout.orientation)

        binding.apply {
            rvList.layoutManager = layout
            rvList.addItemDecoration(itemDecoration)
            binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                private val coroutineScope = CoroutineScope(Dispatchers.Main)
                private var searchJob: Job? = null

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        newText.let {
                            delay(500)
                            if (newText != null && newText.isNotEmpty()) {
                                viewModel.searchUser(newText)
                            } else{
                                binding.svSearch.clearFocus()
                                viewModel.getUsers()
                            }
                        }
                    }
                    return true
                }
            })
        }
    }

    private fun subscribe() {
        viewModel.apply {
            isLoading.observe(this@MainActivity) { showLoading(it) }
            users.observe(this@MainActivity) {
                if (it != null && it.isNotEmpty()) {
                    setUserData(it)
                    showMessage(false)
                } else {
                    showMessage(true)
                }
            }
            searchedUsers.observe(this@MainActivity) {
                if (it.isNotEmpty() && it != null) {
                    setUserData(it)
                    showMessage(false)
                } else {
                    showMessage(true)
                }
            }
        }
    }

    private fun setUserData(users: List<UserModel>?) {
        val userAdapter = UserAdapter(users!!)
        binding.rvList.adapter = userAdapter
        userAdapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserModel) {
                DetailActivity.start(this@MainActivity, user.username!!)
            }
        })
    }

    private fun showMessage(isShowMessage: Boolean) {
        if (isShowMessage) {
            binding.apply {
                tvMessage.visibility = View.VISIBLE
                rvList.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                tvMessage.visibility = View.INVISIBLE
                rvList.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                tvMessage.visibility = View.INVISIBLE
                rvList.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                tvMessage.visibility = View.VISIBLE
                rvList.visibility = View.VISIBLE
            }
        }
    }
}
