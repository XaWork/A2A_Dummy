package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class ZipListModel(
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        @SerializedName("additional_cost")
        val additionalCost: String, // 10
        val city: String, // 615097ec4426a70009eb380f
        val cod: Boolean, // true
        @SerializedName("created_date")
        val createdDate: String, // 2021-12-15T19:24:51.783Z
        val deleted: Int, // 0
        val express: Boolean, // true
        @SerializedName("_id")
        val id: String, // 61ba42b656f91c00099dc609
        val name: String, // 110001
        @SerializedName("superfast_delivery")
        val superfastDelivery: Boolean, // true
        val timeslot: List<Any>,
        val timeslot2: List<Any>,
        @SerializedName("update_date")
        val updateDate: String, // 2021-12-15T19:24:51.783Z
        @SerializedName("__v")
        val v: Int // 0
    )
}