package com.a2a.app.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.SingleCheckoutItemBinding

class CheckoutAdapter(
    private val context: Context,
    private var data: MutableList<CommonModel>,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<CheckoutAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleCheckoutItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, itemClick: RvItemClick) {

            with(viewBinding) {
            }
        }
    }

    fun updateDataList(dataList: List<CommonModel>?) {
        data.clear()
        data.addAll(dataList!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(
            SingleCheckoutItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick)
    }

    override fun getItemCount() = data.size
}