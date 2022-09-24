package com.a2a.app.data.model

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
            val pickup_date: String,
            val remarks: String,
            val time: String
        )
    }
}