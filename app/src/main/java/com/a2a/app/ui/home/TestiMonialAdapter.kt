package com.a2a.app.ui.home

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.R
import com.a2a.app.data.model.HomeModel
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleTestimonialBinding
import com.bumptech.glide.Glide
import com.onesignal.HTML

class TestiMonialAdapter(
    private val data: List<HomeModel.Result.Testimonial>,
    private val context: Context
) : RecyclerView.Adapter<TestiMonialAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleTestimonialBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, testimonial: HomeModel.Result.Testimonial) {
            with(viewBinding) {
                testimonial.run {
                    Glide
                        .with(context)
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .into(imgTestimonial)

                    tvTestimonialTitle.text = name
                    tvTestimonialDescription.text = Html.fromHtml(message, 0)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleTestimonialBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, data[position])
        if (position == 0) {
            Log.d("position", position.toString())
            val padding: Int = context.resources.getDimensionPixelOffset(R.dimen.rvAfterPadding)
            holder.itemView.setPadding(padding, 0, padding, 0)
        }
    }

    override fun getItemCount() = data.size
}