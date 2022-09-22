package com.example.fundamentalsubmission

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.databinding.ActivityMainBinding
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.presentation.ui.DetailActivity
import com.example.fundamentalsubmission.presentation.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "GitHub Users"

        viewModel.isLoading.observe(this) { showLoading(it) }

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svSearch.setActivated(true)
        binding.svSearch.onActionViewExpanded()
        binding.svSearch.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchUser(newText)
                    viewModel.searchedUsers.observe(this@MainActivity) {
                        if (it != null) {
                            setUserData(it)
                            showMessage(false, "")
                        } else {
                            showMessage(true, getString(R.string.user_not_found))
                        }
                    }
                } else {
                    showMessage(true)
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.rvList.visibility = View.INVISIBLE
                }
                return true
            }
        })
    }

    private fun setUserData(user: List<UserModel>) {
        val userAdapter = UserAdapter(user)
        binding.rvList.adapter = userAdapter
        userAdapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserModel) {
                gotoDetailActivity(user)
            }
        })
    }

    private fun showMessage(
        isShowMessage: Boolean,
        text: String = getString(R.string.search_message)
    ) {
        binding.tvMessage.text = text

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
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.tvMessage.visibility = View.VISIBLE
        }
    }

    private fun gotoDetailActivity(user: UserModel) {
        val moveIntent = Intent(this, DetailActivity::class.java)
        startActivity(moveIntent)
    }
}
