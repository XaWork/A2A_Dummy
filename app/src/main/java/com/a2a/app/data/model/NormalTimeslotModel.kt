package com.a2a.app.data.model

data class NormalTimeslotModel(
    val status: String,
    val timeslot: List<Timeslot>
) {
    data class Timeslot(
        val pickup: Pickup
    ) {
        data class Pickup(
            val pickup_date: String,
            val time: String
        )
    }
}