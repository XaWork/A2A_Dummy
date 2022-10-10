package com.a2a.app.ui.home

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.R
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.HomeModel
import com.a2a.app.databinding.SingleBlogBinding
import com.a2a.app.databinding.SingleCityBinding
import com.a2a.app.toDate
import com.bumptech.glide.Glide

class CityAdapter(
    private val context: Context,
    private val data: List<HomeModel.Result.City>,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<CityAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleCityBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, city: HomeModel.Result.City) {
            with(viewBinding) {
                city.run {
                    Glide.with(context)
                        .load(file)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .into(imgCity)

                    tvTitle.text = name
                }

                itemView.setOnClickListener {
                    itemClick.clickWithPosition("details", position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleCityBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, data[position])
        if (position == 0) {
            val padding: Int = context.resources.getDimensionPixelOffset(R.dimen.medium_padding)
            holder.itemView.setPadding(padding, 0, 0, 0)
        }
    }

    override fun getItemCount() = data.size
}