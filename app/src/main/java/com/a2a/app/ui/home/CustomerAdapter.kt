package com.a2a.app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.R
import com.a2a.app.data.model.HomeModel
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleCustomerBinding
import com.a2a.app.databinding.SingleTestimonialBinding
import com.bumptech.glide.Glide

class CustomerAdapter(
    private val context: Context,
    private val data: List<HomeModel.Result.Client>
) : RecyclerView.Adapter<CustomerAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleCustomerBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, client: HomeModel.Result.Client) {
            with(viewBinding) {
                client.run {
                    Glide.with(context)
                        .load(file)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .into(imgClient)

                    tvClientName.text = name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleCustomerBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, data[position])
        if (position == 0) {
            val padding: Int = context.resources.getDimensionPixelOffset(R.dimen.medium_padding)
            holder.itemView.setPadding(padding, 0, 0, 0)
        }
    }

    override fun getItemCount() = data.size
}