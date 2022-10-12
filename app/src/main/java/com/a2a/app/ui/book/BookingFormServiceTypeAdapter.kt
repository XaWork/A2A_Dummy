package com.a2a.app.ui.book

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.common.RvItemClick
import com.a2a.app.databinding.SingleBookingFormServiceBinding


class BookingFormServiceTypeAdapter(
    private var data: List<String>,
    private val context: Context,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<BookingFormServiceTypeAdapter.MyViewHolder>() {

    private var selectedPosition = -1

    inner class MyViewHolder(private val viewBinding: SingleBookingFormServiceBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(position: Int, itemClick: RvItemClick, serviceName: String) {
            with(viewBinding) {
                rbService.text = serviceName.capitalize()
                rbService.isChecked = position == selectedPosition

                rbService.setOnCheckedChangeListener { _, b ->
                    //Log.e("Booking service adapter", rbService.text.toString(), )
                    if (b) {
                        selectedPosition = adapterPosition
                        notifyDataSetChanged()
                        itemClick.clickWithPosition(rbService.text.toString(), position)
                    }
                }
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(
            SingleBookingFormServiceBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, itemClick, data[position])
    }

    override fun getItemCount() = data.size
}