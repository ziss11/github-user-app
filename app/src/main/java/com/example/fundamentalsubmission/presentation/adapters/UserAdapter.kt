package com.example.fundamentalsubmission.presentation.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fundamentalsubmission.databinding.ItemUserBinding
import com.example.fundamentalsubmission.data.models.UserModel
import com.example.fundamentalsubmission.utilities.UserDIffCallback
import com.example.fundamentalsubmission.utilities.loadImage

class UserAdapter(val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<UserModel>()

    fun setListUsers(listUser: List<UserModel>) {
        val diffCallback = UserDIffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.binding.userAvatar.loadImage(holder.itemView.context, user.avatar)
        holder.binding.tvUserUname.text = user.username
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[position])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserModel)
    }
}