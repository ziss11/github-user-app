package com.example.fundamentalsubmission.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.models.User
import com.example.fundamentalsubmission.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail User"

        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        Glide.with(this).load(user?.photo).into(binding.imgDetail)

        val followers = "${user?.followers} ${getString(R.string.followers_plural)}"
        val followings = "${user?.following} ${getString(R.string.followings_plural)}"
        val repositories = "${user?.repositories} ${getString(R.string.repositories_plural)}"

        binding.tvUserName.text = user?.name
        binding.tvUserUname.text = user?.username
        binding.tvFollowers.text = followers
        binding.tvFollowing.text = followings
        binding.tvRepositories.text = repositories
        binding.tvUserCompany.text = user?.company
        binding.tvLocation.text = user?.location
    }

    companion object{
        const val EXTRA_USER = "extra_user"
    }
}