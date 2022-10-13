package com.example.fundamentalsubmission.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.MainActivity
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.databinding.FragmentFolllowBinding
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.presentation.ui.activity.DetailActivity
import com.example.fundamentalsubmission.presentation.viewmodels.DetailViewModel
import com.example.fundamentalsubmission.presentation.viewmodels.ViewModelFactory
import com.example.fundamentalsubmission.utilities.ResultState

class FollowFragment : Fragment() {
    private var _binding: FragmentFolllowBinding? = null
    private val binding get() = _binding!!

    private var factory: ViewModelFactory? = null
    private val viewModel: DetailViewModel by viewModels { factory as ViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFolllowBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance()

        val layout = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layout.orientation)

        binding.rvUsers.apply {
            layoutManager = layout
            addItemDecoration(itemDecoration)
        }

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(EXTRA_USERNAME)

        if (index == 1) {
            getFollowers(username!!)
        } else if (index == 2) {
            getFollowing(username!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getFollowers(username: String) {
        viewModel.fetchUserFollowers(username).observe(requireActivity()) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    if (result.data.isNotEmpty()) {
                        showLoading(false)
                        showMessage(getString(R.string.fol_empty, "followers"))
                        setUserData(result.data)
                    } else {
                        showLoading(false)
                        showMessage(getString(R.string.fol_empty, "followers"))
                    }
                }
                is ResultState.Error -> {
                    Log.d(TAG, result.message)
                    showMessage(getString(R.string.fol_empty, "followers"))
                }
            }
        }
    }

    private fun getFollowing(username: String) {
        viewModel.fetchUserFollowing(username).observe(requireActivity()) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    if (result.data.isNotEmpty()) {
                        showLoading(false)
                        showMessage(getString(R.string.fol_empty, "followers"))
                        setUserData(result.data)
                    } else {
                        showLoading(false)
                        showMessage(getString(R.string.fol_empty, "followers"))
                    }
                }
                is ResultState.Error -> {
                    Log.d(TAG, result.message)
                    showMessage(getString(R.string.fol_empty, "followers"))
                }
            }
        }
    }

    private fun setUserData(users: List<UserModel>?) {
        val userAdapter = UserAdapter(users!!)
        binding.rvUsers.adapter = userAdapter
        userAdapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserModel) {
                DetailActivity.start(requireActivity(), user.username!!)
            }
        })
    }

    private fun showMessage(text: String) {
        binding.apply {
            tvMessage.text = text
            tvMessage.visibility = View.VISIBLE
            rvUsers.visibility = View.INVISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        private var TAG = this::class.java.simpleName

        const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USERNAME = "extra_username"
    }
}