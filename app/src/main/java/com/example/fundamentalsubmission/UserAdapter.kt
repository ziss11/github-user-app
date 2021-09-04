package com.example.fundamentalsubmission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (private val listUser: ArrayList<GitHubUser>): RecyclerView.Adapter<UserAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemCallback(onItemCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemCallback
    }

    class ListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        var tvUname: TextView = itemView.findViewById(R.id.tv_user_uname)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_user_company)
        var userPhoto: CircleImageView = itemView.findViewById(R.id.user_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context).load(user.photo).apply(RequestOptions().override(90,90)).into(holder.userPhoto)

        holder.tvUserName.text = user.name
        holder.tvUname.text = user.username
        holder.tvCompany.text = user.company
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listUser[position])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: GitHubUser)
    }

}