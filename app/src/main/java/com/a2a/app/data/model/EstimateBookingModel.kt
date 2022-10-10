package com.a2a.app.data.model

import com.facebook.common.logging.FLog

data class EstimateBookingModel(
    val estimations: List<Estimation>,
    val status: String
) {
    data class Estimation(
        val delivery: Delivery,
        val pickup: Pickup
    ) {
        data class Delivery(
            val delivery_date: String,
            val delivery_slot: String
        )

        data class Pickup(
            val estimated_price: Float,
            val estimated_price_with_gst: EstimatedPriceWithGst,
            val pickup_date: String,
            val remarks: String,
            val time: String
        ) {
            data class EstimatedPriceWithGst(
                val cgst: Float,
                val estimated_price: Float,
                val igst: Float,
                val sgst: Float
            )
        }
    }
}