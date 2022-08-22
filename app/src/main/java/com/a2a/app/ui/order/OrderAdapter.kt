package com.a2a.app.ui.order

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.CommonModel
import com.a2a.app.data.model.OrderModel
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleOrderItemBinding
import com.bumptech.glide.Glide

class OrderAdapter(
    private val data: List<OrderModel.Result>,
    private val context: Context,
    private val itemClick: RvItemClick
)
    : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleOrderItemBinding):
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(position: Int, itemClick: RvItemClick){
                with(viewBinding){
                    orderItem = data[position]
                }

                itemView.setOnClickListener{
                    itemClick.clickWithPosition("Order item", position)
                }
            }
        }

/*    fun updateDataList(dataList: List<CommonModel>){
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        MyViewHolder(SingleOrderItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick)
    }

    override fun getItemCount() = data.size

    fun moveToOrderDetails(
        orderId: Int
    ){
        val activity = context as Activity

    }
}