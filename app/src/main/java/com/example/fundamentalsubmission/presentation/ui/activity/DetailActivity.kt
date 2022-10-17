package com.example.fundamentalsubmission.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.dataStore
import com.example.fundamentalsubmission.databinding.ActivityDetailBinding
import com.example.fundamentalsubmission.presentation.adapters.SectionsPageAdapter
import com.example.fundamentalsubmission.presentation.viewmodels.DetailViewModel
import com.example.fundamentalsubmission.presentation.viewmodels.ViewModelFactory
import com.example.fundamentalsubmission.utilities.ResultState
import com.example.fundamentalsubmission.utilities.loadImage
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: DetailViewModel by viewModels { factory }

    private var username: String? = null
    private var userData: UserModel? = null
    private var isFavorite = false

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        factory = ViewModelFactory.getInstance(this, dataStore)

        username = intent.getStringExtra(EXTRA_USERNAME) as String

        val sectionsPageAdapter = SectionsPageAdapter(this, username!!)
        binding.viewPager.adapter = sectionsPageAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onStart() {
        super.onStart()
        getUserDetails(username!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu

        checkFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.set_favorite_action -> {
                if (isFavorite) {
                    viewModel.removeFromFavorite(username!!)
                } else {
                    viewModel.addToFavorite(userData!!)
                }
            }
            android.R.id.home -> finishAndRemoveTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkFavorite() {
        viewModel.isFavoriteUser(username!!).observe(this) {
            if (it) {
                isFavorite = it
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            } else {
                isFavorite = it
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            }
        }
    }

    private fun getUserDetails(username: String) {
        viewModel.getDetailUser(username).observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    showMessage(false)
                    userData = result.data
                    setUserData(userData)
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showMessage(true)
                }
            }
        }
    }

    private fun setUserData(user: UserModel?) {
        val headerItem = binding.headerItem

        headerItem.apply {
            ivUserAvatar.loadImage(user?.avatar)
            tvUserName.text = user?.name
            tvUserUname.text = user?.username
            tvRepositories.text = user?.publicRepos.toString()
            tvFollowers.text = user?.followers.toString()
            tvFollowings.text = user?.following.toString()

            if (user?.bio == null) {
                tvBio.visibility = View.GONE
            } else {
                tvBio.text = user.bio
            }

            if (user?.location == null) {
                location.visibility = View.GONE
            } else {
                tvLocation.text = user.location
            }

            if (user?.company == null) {
                company.visibility = View.GONE
            } else {
                tvCompany.text = user.company
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.headerItem.apply {
                progressBar.visibility = View.VISIBLE
                tvMessage.visibility = View.GONE
                userDetail.visibility = View.GONE
            }
        } else {
            binding.headerItem.apply {
                progressBar.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                userDetail.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(isShowMessage: Boolean) {
        if (isShowMessage) {
            binding.headerItem.apply {
                tvMessage.visibility = View.VISIBLE
                userDetail.visibility = View.GONE
            }
        } else {
            binding.headerItem.apply {
                tvMessage.visibility = View.GONE
                userDetail.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following,
        )

        fun start(context: Context, username: String) {
            Intent(context, DetailActivity::class.java).apply {
                this.putExtra(EXTRA_USERNAME, username)
                context.startActivity(this)
            }
        }
    }
}