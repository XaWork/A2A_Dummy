package com.a2a.app.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class OrderUpdateModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val updates: List<Result>,
    @SerializedName("status")
    val status: String
){
    @Keep
    data class Result(
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("order")
        val order: String,
        @SerializedName("__v")
        val v: Int
    )
}
