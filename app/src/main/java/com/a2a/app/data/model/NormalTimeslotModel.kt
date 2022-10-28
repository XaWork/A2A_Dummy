package com.a2a.app.data.model

data class NormalTimeslotModel(
    val cod: Boolean,
    val express_slots: ExpressSlots,
    val normal_slots: NormalSlots,
    val status: String,
    val super_slots: SuperSlots
) {
    data class ExpressSlots(
        val enabled: Boolean,
        val slots: List<Any>
    )

    data class NormalSlots(
        val enabled: Boolean,
        val slots: List<Slot>
    ) {
        data class Slot(
            val pickup: Pickup
        ) {
            data class Pickup(
                val pickup_date: String,
                val time: String
            )
        }
    }

    data class SuperSlots(
        val enabled: Boolean,
        val slots: List<Any>
    )
}