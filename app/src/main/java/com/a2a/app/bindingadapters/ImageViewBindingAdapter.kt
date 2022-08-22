package com.a2a.app.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.a2a.app.R
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImageFromUrl(view: ImageView, url: String){
   // Log.d("imageURL", url)
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.image_error)
        .into(view)
}