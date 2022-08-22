package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class WalletDataModel(
    @SerializedName("customer_point")
    val customerPoint: Int, // 0
    val plan: Plan,
    @SerializedName("point_settings")
    val pointSettings: PointSettings
) {
    data class Plan(
        @SerializedName("cart_max_price")
        val cartMaxPrice: Int, // 0
        @SerializedName("cart_min_price")
        val cartMinPrice: Int, // 0
        @SerializedName("discount_percentage")
        val discountPercentage: Int, // 0
        @SerializedName("exp_date")
        val expDate: Any?, // null
        @SerializedName("plan_expired")
        val planExpired: Boolean, // true
        @SerializedName("plan_name")
        val planName: String,
        @SerializedName("plan_price")
        val planPrice: String
    )

    data class PointSettings(
        @SerializedName("max_redeem_point_per_order")
        val maxRedeemPointPerOrder: String, // 0
        @SerializedName("one_point_value_in_rupess")
        val onePointValueInRupess: String, // 0.1
        @SerializedName("point_redeem_maximum_order_value")
        val pointRedeemMaximumOrderValue: String, // 1
        @SerializedName("point_redeem_minimum_order_value")
        val pointRedeemMinimumOrderValue: String // 1
    )

    val walletBalance:String
        get() = "₹ ${customerPoint * pointSettings.onePointValueInRupess.toFloat()}"
}