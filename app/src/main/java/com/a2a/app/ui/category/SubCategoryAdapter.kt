package com.a2a.app.ui.category

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
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.SingleCommonBinding
import com.a2a.app.databinding.SingleSubcategoryItemBinding
import com.bumptech.glide.Glide

class SubCategoryAdapter(
    private val context: Context,
    private var data: List<AllSubCategoryModel.Result>
)
    : RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleSubcategoryItemBinding):
        RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(singleSubCategory: AllSubCategoryModel.Result,position: Int){

                with(viewBinding){
                    Glide.with(context)
                        .load(singleSubCategory.file)
                        .error(R.drawable.image_error)
                        .placeholder(R.drawable.image_placeholder)
                        .into(ivImage)

                    tvTitle.text = singleSubCategory.name
                    tvDescription.text = singleSubCategory.description
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        MyViewHolder(SingleSubcategoryItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position],position)
    }

    override fun getItemCount() = data.size
}