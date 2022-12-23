package com.a2a.app.ui.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2a.app.common.RvItemClick
import com.a2a.app.data.model.WalletTransactionModel
import com.a2a.app.databinding.TransactionItemBinding

class TransactionAdapter(
    private var data: List<WalletTransactionModel.Transaction>,
    private val context: Context,
    private val itemClick: RvItemClick
) : RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val viewBinding: TransactionItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(
            position: Int,
            transactionItem: WalletTransactionModel.Transaction,
            itemClick: RvItemClick
        ) {

            with(viewBinding) {
                val transactionHeading =
                    if (transactionItem.type == 0) "Points Earned" else "Points Redeemed"
                transactionType.text = transactionHeading
                transactionNotes.text = transactionItem.note
                points.text = "${transactionItem.point} Points"
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position, data[position], itemClick)
    }

    override fun getItemCount() = data.size
}