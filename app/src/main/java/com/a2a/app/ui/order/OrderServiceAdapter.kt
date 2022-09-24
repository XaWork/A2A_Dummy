package com.a2a.app.ui.order

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
import com.a2a.app.databinding.SingleOrderServiceBinding
import com.bumptech.glide.Glide

class OrderServiceAdapter(
    private var data: List<String>,
    private val context: Context,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<OrderServiceAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleOrderServiceBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, itemClick: RvItemClick, service: String) {
            viewBinding.tvServiceName.text = service
            itemView.setOnClickListener {
                itemClick.clickWithPosition(service, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleOrderServiceBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick, data[position])
    }

    override fun getItemCount() = data.size
}