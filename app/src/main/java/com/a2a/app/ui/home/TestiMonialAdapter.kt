package com.a2a.app.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.R
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleTestimonialBinding

class TestiMonialAdapter(
    private val data: List<String>,
    private val context: Context
)
    : RecyclerView.Adapter<TestiMonialAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleTestimonialBinding):
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(position: Int){

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        MyViewHolder(SingleTestimonialBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
        if(position == 0){
            Log.d("position", position.toString())
            val padding: Int = context.resources.getDimensionPixelOffset(R.dimen.rvAfterPadding)
            holder.itemView.setPadding(padding, 0,padding,0)
        }
    }

    override fun getItemCount() = data.size
}