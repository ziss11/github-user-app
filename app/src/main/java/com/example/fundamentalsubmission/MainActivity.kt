package com.example.fundamentalsubmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.databinding.ActivityMainBinding
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.presentation.ui.activity.DetailActivity
import com.example.fundamentalsubmission.presentation.ui.activity.FavoriteActivity
import com.example.fundamentalsubmission.presentation.viewmodels.MainViewModel
import com.example.fundamentalsubmission.presentation.viewmodels.ViewModelFactory
import com.example.fundamentalsubmission.utilities.ResultState
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var userAdapter: UserAdapter

    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.search_users)

        factory = ViewModelFactory.getInstance(this)
        fetchUsers()

        val layout = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layout.orientation)

        userAdapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserModel) {
                DetailActivity.start(this@MainActivity, user.username!!)
            }
        })


        binding.apply {
            rvList.adapter = userAdapter
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
                                fetchSearchedUsers(newText)
                            } else {
                                binding.svSearch.clearFocus()
                                fetchUsers()
                            }
                        }
                    }
                    return true
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.theme_action -> {}
            R.id.favorite_action -> FavoriteActivity.start(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchUsers() {
        viewModel.fetchUsers().observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    if (result.data.isNotEmpty()) {
                        showLoading(false)
                        showMessage(false)
                        userAdapter.setListUsers(result.data)
                    } else {
                        showLoading(false)
                        showMessage(true)
                    }
                }
                is ResultState.Error -> {
                    showMessage(true)
                }
            }
        }
    }

    private fun fetchSearchedUsers(query: String) {
        viewModel.fetchSearchedUsers(query).observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    if (result.data.isNotEmpty()) {
                        showLoading(false)
                        showMessage(false)
                        userAdapter.setListUsers(result.data)
                    } else {
                        showLoading(false)
                        showMessage(true)
                    }
                }
                is ResultState.Error -> {
                    showMessage(true)
                }
            }
        }
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
