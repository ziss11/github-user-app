package com.example.fundamentalsubmission.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.dataStore
import com.example.fundamentalsubmission.databinding.ActivityFavoriteBinding
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.presentation.viewmodels.FavoriteViewModel
import com.example.fundamentalsubmission.presentation.viewmodels.ViewModelFactory
import com.example.fundamentalsubmission.utilities.ResultState
import com.example.fundamentalsubmission.utilities.toModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var userAdapter: UserAdapter

    private val viewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.favorite_title)
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        factory = ViewModelFactory.getInstance(this, dataStore)
        fetchFavoriteUsers()

        val layout = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layout.orientation)

        userAdapter = UserAdapter(onItemClickCallback = object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserModel) {
                DetailActivity.start(this@FavoriteActivity, user.username!!)
            }
        })

        binding.apply {
            rvFavoriteUser.adapter = userAdapter
            rvFavoriteUser.layoutManager = layout
            rvFavoriteUser.addItemDecoration(itemDecoration)
        }
    }

    private fun fetchFavoriteUsers() {
        viewModel.fetchFavoriteUsers().observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    if (result.data.isNotEmpty()) {
                        val userModel = result.data.map { it.toModel() }
                        showLoading(false)
                        showMessage(false)
                        userAdapter.setListUsers(userModel)
                    } else {
                        showLoading(false)
                        showMessage(true)
                    }
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showMessage(true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                tvMessage.visibility = View.INVISIBLE
                rvFavoriteUser.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                tvMessage.visibility = View.VISIBLE
                rvFavoriteUser.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(isShowMessage: Boolean) {
        if (isShowMessage) {
            binding.apply {
                tvMessage.visibility = View.VISIBLE
                rvFavoriteUser.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                tvMessage.visibility = View.INVISIBLE
                rvFavoriteUser.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        fun start(context: Context) {
            Intent(context, FavoriteActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}