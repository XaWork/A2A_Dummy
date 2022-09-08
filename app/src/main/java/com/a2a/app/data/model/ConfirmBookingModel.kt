package com.a2a.app.data.model

data class ConfirmBookingModel(
    val orderId: String,
    val status: String,
    val timeslot: String
)