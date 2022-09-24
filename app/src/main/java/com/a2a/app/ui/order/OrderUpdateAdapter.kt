package com.a2a.app.ui.order

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
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.ItemIntermediateBinding
import com.a2a.app.databinding.SingleCommonBinding
import com.bumptech.glide.Glide

class OrderUpdateAdapter(
    private val context: Context,
    private val updates: List<Updates>
) : RecyclerView.Adapter<OrderUpdateAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: ItemIntermediateBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(update: Updates) {
            with(viewBinding) {
                updateTextValue.text = update.item
                when (update.updateType) {
                    UpdateType.EXIT -> {
                        exit.visibility = View.VISIBLE
                        entry.visibility = View.GONE
                        intermediate.visibility = View.GONE
                    }
                    UpdateType.ENTRY -> {
                        exit.visibility = View.GONE
                        entry.visibility = View.VISIBLE
                        intermediate.visibility = View.GONE
                    }
                    else -> {
                        exit.visibility = View.GONE
                        entry.visibility = View.GONE
                        intermediate.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemIntermediateBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(updates[position])
    }

    override fun getItemCount() = updates.size
}

enum class UpdateType {
    ENTRY,
    EXIT,
    INTERMEDIATE
}

data class Updates(val item : String, val updateType: UpdateType)