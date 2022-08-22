package com.a2a.app.ui.address

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.BR
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.databinding.SingleAddressBinding

class AddressAdapter(
    private val context: Context,
    private var data: List<AddressListModel.Result>,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: SingleAddressBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, itemClick: RvItemClick) {

            with(viewBinding) {
                setVariable(BR.address, data[position])
                executePendingBindings()

                btnDeleteAddress.setOnClickListener{
                    itemClick.clickWithPosition("delete", position)
                }
                btnEditAddress.setOnClickListener {
                    itemClick.clickWithPosition("edit", position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(SingleAddressBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick)
    }

    override fun getItemCount() = data.size
}