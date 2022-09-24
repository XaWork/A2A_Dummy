package com.a2a.app.ui.home

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.a2a.app.R
import com.a2a.app.common.ItemClick
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.HomeModel
import com.a2a.app.databinding.SingleBlogBinding
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleCustomerBinding
import com.a2a.app.databinding.SingleTestimonialBinding
import com.a2a.app.toDate
import com.bumptech.glide.Glide

class BlogAdapter(
    private val context: Context,
    private val data: List<HomeModel.Result.Blog>,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<BlogAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleBlogBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, blog: HomeModel.Result.Blog) {
            with(viewBinding) {
                blog.run {
                    Glide.with(context)
                        .load(image)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .into(imgBlog)

                    tvBlogTitle.text = title
                    tvBlogDesc.text = Html.fromHtml(description, 0)
                    tvDate.text = createdAt.toDate("dd-MM-yyyy")
                }

                itemView.setOnClickListener {
                    itemClick.clickWithPosition("details", position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleBlogBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, data[position])
        if (position == 0) {
            val padding: Int = context.resources.getDimensionPixelOffset(R.dimen.medium_padding)
            holder.itemView.setPadding(padding, 0, 0, 0)
        }
    }

    override fun getItemCount() = data.size
}