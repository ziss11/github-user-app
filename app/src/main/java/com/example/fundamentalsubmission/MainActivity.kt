package com.example.fundamentalsubmission

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.databinding.ActivityMainBinding
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.presentation.ui.activity.DetailActivity
import com.example.fundamentalsubmission.presentation.ui.activity.FavoriteActivity
import com.example.fundamentalsubmission.presentation.viewmodels.MainViewModel
import com.example.fundamentalsubmission.presentation.viewmodels.ViewModelFactory
import com.example.fundamentalsubmission.utilities.ResultState
import kotlinx.coroutines.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var userAdapter: UserAdapter

    private val mainViewModel: MainViewModel by viewModels { factory }

    private var themeMode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.search_users)

        factory = ViewModelFactory.getInstance(this, dataStore)
        getThemeData()
        fetchUsers()

        val layout = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layout.orientation)

        userAdapter = UserAdapter(onItemClickCallback = object : UserAdapter.OnItemClickCallback {
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
            R.id.setting_action -> chooseThemeDialog()
            R.id.favorite_action -> FavoriteActivity.start(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getThemeData() {
        mainViewModel.getThemeSetting().observe(this) {
            themeMode = it

            when (it) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }

    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme))

        val items = arrayOf("Light", "Dark")

        builder.setSingleChoiceItems(items, themeMode) { dialog, itemId ->
            mainViewModel.saveThemeSetting(itemId)
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun fetchUsers() {
        mainViewModel.fetchUsers().observe(this) { result ->
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
        mainViewModel.fetchSearchedUsers(query).observe(this) { result ->
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
