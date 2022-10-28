package com.a2a.app.data.model

data class EstimateBookingModel(
    val delivery_date: String,
    val delivery_slots: List<String>,
    val estimations: Estimations,
    val remarks: String,
    val status: String
) {
    data class Estimations(
        val booking_price: Int,
        val estimated_price: String,
        val estimated_price_with_gst: EstimatedPriceWithGst,
        val live_temparature: Int,
        val live_tracking: Int,
        val pickup_price: Int,
        val picture_recording: Int,
        val video_recording: Int
    ) {
        data class EstimatedPriceWithGst(
            val cgst: String,
            val estimated_price: String,
            val igst: String,
            val sgst: String
        )
    }
}