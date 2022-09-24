package com.a2a.app.ui.membership

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
import com.a2a.app.common.ItemClick
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.AllPlanModel
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.PlanItemBinding
import com.a2a.app.databinding.SingleCommonBinding
import com.bumptech.glide.Glide

class PlanAdapter(
    private var data: List<AllPlanModel.Result>,
    private val context: Context,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: PlanItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, itemClick: RvItemClick, plan: AllPlanModel.Result) {

            with(viewBinding) {
                plan.run {
                    planName.text = name
                    descriptionText.text = description
                    validityText.text = "Validity\n$day"
                    planPrice.text = price.toString()
                    var benefitCities = ""
                    for (i in city) {
                        benefitCities += "${i.name}, "
                    }
                    benefitsContent.text = benefitCities
                }
                buyButton.setOnClickListener {
                    itemClick.clickWithPosition("buy", position)
                }
            }
            /*itemView.setOnClickListener {
                itemClick.clickWithPosition("", position)
            }*/
            /*viewBinding.btnViewDetails.setOnClickListener {
                itemClick.clickWithPosition("details", position)
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(PlanItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick, data[position])
    }

    override fun getItemCount() = data.size
}