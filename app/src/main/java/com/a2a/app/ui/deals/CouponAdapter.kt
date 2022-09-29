package com.a2a.app.ui.deals

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.BR
import com.a2a.app.R
import com.a2a.app.common.ItemClick
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleCouponItemBinding
import com.a2a.app.databinding.SingleDealBinding
import com.bumptech.glide.Glide

class CouponAdapter(
    private val context: Context
) : RecyclerView.Adapter<CouponAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleCouponItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind( position: Int) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleCouponItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 3
}