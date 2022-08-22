package com.a2a.app.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.R
import com.a2a.app.databinding.CarousalLayoutBinding
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleCustomerBinding
import com.a2a.app.databinding.SingleTestimonialBinding
import com.a2a.app.ui.customeviews.AutoScrollViewPager

class CarousalAdapter(
    private val data:List<Int>,
    private val context: Context
)
    : RecyclerView.Adapter<CarousalAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: CarousalLayoutBinding):
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(position: Int){
                with(viewBinding){
                    homeVp.adapter =
                        CarousalImagePagerAdapter(context, data)
                    homeVp.setInterval(4000)
                    homeVp.setDirection(AutoScrollViewPager.Direction.RIGHT)
                    homeVp.setBorderAnimation(true)
                    homeVp.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
                    homeVp.startAutoScroll()
                    indicator.setViewPager(homeVp)
                }

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        MyViewHolder(CarousalLayoutBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 1
}