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

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        binding.rvList.layoutManager = layoutManager
        binding.rvList.addItemDecoration(itemDecoration)

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
                        } else {
                            binding.svSearch.clearFocus()
                            showMessage(true)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun subscribe() {
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.searchedUsers.observe(this) {
            if (it.isNotEmpty() && it != null) {
                setUserData(it)
                showMessage(false)
            } else {
                showMessage(true)
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
            binding.tvMessage.visibility = View.VISIBLE
            binding.rvList.visibility = View.INVISIBLE
        } else {
            binding.tvMessage.visibility = View.INVISIBLE
            binding.rvList.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.tvMessage.visibility = View.INVISIBLE
            binding.rvList.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.tvMessage.visibility = View.VISIBLE
            binding.rvList.visibility = View.VISIBLE
        }
    }
}
