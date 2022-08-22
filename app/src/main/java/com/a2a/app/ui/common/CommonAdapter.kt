package com.a2a.app.ui.common

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
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.SingleCommonBinding
import com.bumptech.glide.Glide

class CommonAdapter(
    private var data: MutableList<CommonModel>,
    private val context: Context,
    private val itemClick: RvItemClick
)
    : RecyclerView.Adapter<CommonAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleCommonBinding):
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(position: Int, itemClick: RvItemClick){

                with(viewBinding){
                setVariable(BR.common, data[position])
                    //common = data[position]
                    executePendingBindings()
                }
                itemView.setOnClickListener{
                    itemClick.clickWithPosition("", position)
                }
            }
        }

    fun updateDataList(dataList: List<CommonModel>?){
        data.clear()
        data.addAll(dataList!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        MyViewHolder(SingleCommonBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick)
    }

    override fun getItemCount() = data.size
}