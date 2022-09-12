package com.example.fundamentalsubmission.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fundamentalsubmission.databinding.ItemUserBinding
import com.example.fundamentalsubmission.models.User

class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemCallback(onItemCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemCallback
    }

    class ListViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context).load(user.photo)
            .apply(RequestOptions().override(90, 90)).into(holder.binding.userPhoto)

        holder.binding.tvUserName.text = user.name
        holder.binding.tvUserUname.text = user.username
        holder.binding.tvUserCompany.text = user.company
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[position])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

}