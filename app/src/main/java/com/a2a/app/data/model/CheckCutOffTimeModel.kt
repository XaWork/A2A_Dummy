package com.a2a.app.data.model

import android.annotation.SuppressLint
import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

data class CheckCutOffTimeModel(
    val message: String,
    val result: Result,
    val status: String
){
    @Keep
    data class Result(
        val __v: Int,
        val _id: String,
        val active: Int,
        val created_date: String,
        val cut_of_time: String,
        val deleted: Int,
        val end_city: String,
        val express: Boolean,
        val express_cut_of_time_first: String,
        val express_cut_of_time_second: String,
        val express_delivery_cost: Int,
        val express_final_delivery_time_first: String,
        val express_final_delivery_time_second: String,
        val express_remarks: String,
        val final_delivery_time: String,
        val normal_delivery_cost: Int,
        val remarks: String,
        val start_city: String,
        val timeslot: String,
        val timeslot_first: String,
        val timeslot_second: String,
        val update_date: String
    )
}

fun CheckCutOffTimeModel.Result.cutOffTime(): Date {
    return formatter.parse(cut_of_time)!!
}

fun CheckCutOffTimeModel.Result.isCutOffTimePassed():Boolean{
    val cutOffTime = cutOffTime()
    val cal = java.util.Calendar.getInstance()
    val currentTime = formatter.parse(formatter.format(cal.time))!!
    return currentTime.after(cutOffTime)
}