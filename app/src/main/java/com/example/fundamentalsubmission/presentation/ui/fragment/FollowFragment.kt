package com.example.fundamentalsubmission.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.databinding.FragmentFolllowBinding
import com.example.fundamentalsubmission.presentation.adapters.UserAdapter
import com.example.fundamentalsubmission.presentation.ui.activity.DetailActivity
import com.example.fundamentalsubmission.presentation.viewmodels.DetailViewModel

class FollowFragment : Fragment() {
    private var _binding: FragmentFolllowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFolllowBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)

        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.addItemDecoration(itemDecoration)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(EXTRA_USERNAME)

        if (index == 1) {
            viewModel.getUserFollowers(username!!)
        } else if (index == 2) {
            viewModel.getUserFollowings(username!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

    private fun subscribe() {
        viewModel.isLoading.observe(requireActivity()) { showLoading(it) }
        viewModel.followers.observe(requireActivity()) {
            if (it != null && it.isNotEmpty()) {
                setUserData(it)
            } else {
                showMessage(getString(R.string.fol_empty, "followers"))
            }
        }
        viewModel.followings.observe(requireActivity()) {
            if (it != null && it.isNotEmpty()) {
                setUserData(it)
            } else {
                showMessage(getString(R.string.fol_empty, "followings"))
            }
        }
    }

    private fun showMessage(text: String) {
        binding.tvMessage.text = text
        binding.tvMessage.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.INVISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}