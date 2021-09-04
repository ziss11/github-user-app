package com.example.fundamentalsubmission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.view.menu.ActionMenuItemView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fundamentalsubmission.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter internal constructor(private val context: Context) : BaseAdapter(){
    internal var users = arrayListOf<GitHubUser>()

    override fun getCount(): Int = users.size

    override fun getItem(position: Int): Any = users[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView

        if(itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)

        }

        val viewHolder = ViewHolder(itemView as View)

        val user = getItem(position) as GitHubUser
        viewHolder.bind(user)
        return itemView
    }

    private inner class ViewHolder constructor(view: View){
        private val binding = ItemUserBinding.bind(view)
         fun bind(user: GitHubUser){
            binding.tvUserName.text = user.name
            binding.tvUserUname.text = user.username
            binding.tvUserCompany.text = "@${user.company}"
            Glide.with(context).load(user.photo).apply(RequestOptions().override(90,90)).into(binding.userPhoto)
        }

    }

}