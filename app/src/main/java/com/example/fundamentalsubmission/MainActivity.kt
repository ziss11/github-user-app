package com.example.fundamentalsubmission

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.fundamentalsubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: UserAdapter

    private lateinit var dataName: Array<String>
    private lateinit var dataUname: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataPhoto: TypedArray
    private lateinit var dataLocation: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataRepositories: Array<String>

    private val users = arrayListOf<GitHubUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "GitHub User's"

        adapter = UserAdapter(this)
        binding.rvList.adapter = adapter

        prepare()
        addItem()

        binding.rvList.onItemClickListener = AdapterView.OnItemClickListener{_,_,position,_ ->
            val moveIntent = Intent(this, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_USER,users[position])
            startActivity(moveIntent)
        }

    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.name)
        dataUname = resources.getStringArray(R.array.username)
        dataCompany = resources.getStringArray(R.array.company)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataLocation = resources.getStringArray(R.array.location)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataRepositories = resources.getStringArray(R.array.repository)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = GitHubUser(
                dataUname[position],
                dataName[position],
                dataCompany[position],
                dataPhoto.getResourceId(position, -1),
                dataLocation[position],
                dataFollowers[position],
                dataFollowing[position],
                dataRepositories[position]
            )
            users.add(user)
        }
        adapter.users = users

    }
}