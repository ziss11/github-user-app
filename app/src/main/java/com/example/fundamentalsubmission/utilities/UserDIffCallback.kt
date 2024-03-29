package com.example.fundamentalsubmission.utilities

import androidx.recyclerview.widget.DiffUtil
import com.example.fundamentalsubmission.data.models.UserModel

class UserDIffCallback(
    private val oldItemList: List<UserModel>,
    private val newItemList: List<UserModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItemList.size
    }

    override fun getNewListSize(): Int {
        return newItemList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemList[oldItemPosition].id == newItemList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemList[oldItemPosition]
        val newItem = newItemList[newItemPosition]
        return oldItem.id == newItem.id && oldItem.username == newItem.username
    }
}