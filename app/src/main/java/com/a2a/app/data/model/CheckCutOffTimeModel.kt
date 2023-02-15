package com.a2a.app.data.model

data class CheckCutOffTimeModel(
    val message: String,
    val result: Result,
    val status: String
) {
    data class Result(
        val __v: Int,
        val _id: String,
        val active: Int,
        val created_date: String,
        val cut_of_time: String,
        val deleted: Int,
        val end_city: String,
        val express: Boolean,
        val express_air_time: String,
        val express_cut_of_time_first: String,
        val express_cut_of_time_second: String,
        val express_delivery_cost: Int,
        val express_final_delivery_time_first: String,
        val express_final_delivery_time_second: String,
        val express_remarks: String,
        val final_delivery_time: String,
        val fixed_price: String,
        val live_temparature: String,
        val live_tracking: String,
        val normal: Boolean,
        val normal_air_time: String,
        val normal_delivery_cost: Int,
        val normal_pickup_delay: String,
        val pickup_express: String,
        val pickup_normal: String,
        val pickup_superfast: String,
        val picture_recording: String,
        val rate_per_km_express: String,
        val rate_per_km_normal: String,
        val rate_per_km_superfast: String,
        val remarks: String,
        val start_city: String,
        val super_cut_of_time_first: String,
        val super_cut_of_time_second: String,
        val super_fast: Boolean,
        val super_fast_delivery_cost: String,
        val super_final_delivery_time_first: String,
        val super_final_delivery_time_second: String,
        val super_timeslot_first: String,
        val super_timeslot_second: String,
        val superfast_air_time: String,
        val superfast_remarks: String,
        val timeslot: String,
        val timeslot_first: String,
        val timeslot_second: String,
        val update_date: String,
        val video_recording: String
    )
}

data class PaidAdditionalServices(
    val pictureRecordingAvailable: Boolean = false,
    val videoRecordingAvailable: Boolean = false,
    val liveGPSTrackingAvailable: Boolean = false,
    val liveTemperatureTrackingAvailable: Boolean = false,
)