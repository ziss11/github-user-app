package com.example.fundamentalsubmission.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fundamentalsubmission.databinding.ItemUserBinding
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.utilities.UserDIffCallback
import com.example.fundamentalsubmission.utilities.loadImage

class UserAdapter(
    val onItemClickCallback: OnItemClickCallback,
) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<UserModel>()

    fun setListUsers(listUser: List<UserModel>) {
        val diffCallback = UserDIffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(
        private val binding: ItemUserBinding,
        private val onItemClickCallback: OnItemClickCallback,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            binding.apply {
                userAvatar.loadImage(user.avatar)
                tvUserUname.text = user.username
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, onItemClickCallback)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserModel)
    }
}