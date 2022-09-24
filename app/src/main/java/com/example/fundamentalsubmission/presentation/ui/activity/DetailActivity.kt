package com.example.fundamentalsubmission.presentation.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.databinding.ActivityDetailBinding
import com.example.fundamentalsubmission.presentation.adapters.SectionsPageAdapter
import com.example.fundamentalsubmission.presentation.viewmodels.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        viewModel.getUserDetail(username!!)

        subscribe()
        val sectionsPageAdapter = SectionsPageAdapter(this, username)
        binding.viewPager.adapter = sectionsPageAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribe() {
        viewModel.user.observe(this) {
            setUserData(it)
        }
    }

    private fun setUserData(user: UserModel?) {
        val headerItem = binding.headerItem

        Glide.with(this).load(user?.avatar).into(headerItem.ivUserAvatar)

        headerItem.tvUserName.text = user?.name
        headerItem.tvUserUname.text = user?.username
        headerItem.tvRepositories.text = user?.publicRepos.toString()
        headerItem.tvFollowers.text = user?.followers.toString()
        headerItem.tvFollowings.text = user?.following.toString()

        if (user?.bio == null) {
            headerItem.tvBio.visibility = View.GONE
        } else {
            headerItem.tvBio.text = user.bio
        }

        if (user?.location == null) {
            headerItem.location.visibility = View.GONE
        } else {
            headerItem.tvLocation.text = user.location
        }

        if (user?.company == null) {
            headerItem.company.visibility = View.GONE
        } else {
            headerItem.tvCompany.text = user.company
        }
    }
}