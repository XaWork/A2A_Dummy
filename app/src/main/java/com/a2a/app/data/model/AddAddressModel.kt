package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class AddAddressModel(
    val message: String, // Address added
    val response: Response,
    val status: String // success
) {
    data class Response(
        val address: String, // Akra
        val address2: String, // Maheshtala
        val city: String, // 615097dc4426a70009eb3802
        @SerializedName("contact_mobile")
        val contactMobile: String, // 8442922399
        @SerializedName("contact_name")
        val contactName: String, // mr xyz
        @SerializedName("created_date")
        val createdDate: String, // 2022-08-05T09:48:06.678Z
        val deleted: Int, // 0
        @SerializedName("_id")
        val id: String, // 62ece82c43a0ea0009e5b80a
        val pincode: String, // 700141
        val position: Position,
        @SerializedName("post_office")
        val postOffice: String, // maheshtala
        val state: String, // 5f665ee12d45902c98aa8f1f
        val title: String, // Delivery Address
        @SerializedName("update_date")
        val updateDate: String, // 2022-08-05T09:48:06.678Z
        val user: String, // 61658eb11174b90008664cb4
        @SerializedName("__v")
        val v: Int // 0
    ) {
        data class Position(
            val coordinates: List<Double>,
            val type: String // Point
        )
    }
}