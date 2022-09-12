package com.example.fundamentalsubmission.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fundamentalsubmission.models.GitHubUser
import com.example.fundamentalsubmission.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail User"

        val user = intent.getParcelableExtra<GitHubUser>(EXTRA_USER) as GitHubUser

        binding.imgDetail.setImageResource(user.photo!!)
        binding.tvUserName.text = user.name
        binding.tvUserUname.text = "@${user.username}"
        binding.tvFollowers.text = "${user.followers} followers |"
        binding.tvFollowing.text = "${user.following} following |"
        binding.tvRepositories.text = "${user.repositories} repositories"
        binding.tvUserCompany.text = user.company
        binding.tvLocation.text = user.location

    }

    companion object{
        const val EXTRA_USER = "extra_user"
    }
}