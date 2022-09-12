package com.example.fundamentalsubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.adapters.UserAdapter
import com.example.fundamentalsubmission.data.UserData
import com.example.fundamentalsubmission.databinding.ActivityMainBinding
import com.example.fundamentalsubmission.models.User
import com.example.fundamentalsubmission.pages.DetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "GitHub Users"

        binding.rvList.setHasFixedSize(true)
        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        users.addAll(UserData.listUser)

        val userAdapter = UserAdapter(users)
        binding.rvList.adapter = userAdapter
        userAdapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                gotoDetailActivity(user)
            }
        })
    }

    private fun gotoDetailActivity(user: User) {
        val moveIntent = Intent(this, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveIntent)
    }
}