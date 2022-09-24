package com.example.fundamentalsubmission.utilities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(context: Context, url: Any?) {
    Glide.with(this.context).load(url).into(this)
}