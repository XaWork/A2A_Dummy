package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class VerifyOtpModel(
    val `data`: Data,
    val message: String, // Otp verified
    val status: String // success
) {
    data class Data(
        val aadhar: String,
        val about: String,
        @SerializedName("acc_holder_name")
        val accHolderName: Any?, // null
        @SerializedName("acc_number")
        val accNumber: Any?, // null
        val active: Int, // 1
        @SerializedName("additional_cost1")
        val additionalCost1: Int, // 0
        @SerializedName("additional_cost2")
        val additionalCost2: Int, // 0
        val address: String,
        @SerializedName("bag_no")
        val bagNo: List<Any>,
        @SerializedName("bank_address")
        val bankAddress: Any?, // null
        @SerializedName("bank_name")
        val bankName: Any?, // null
        @SerializedName("branch_code")
        val branchCode: Any?, // null
        @SerializedName("cargo_position")
        val cargoPosition: CargoPosition,
        val city: Any?, // null
        val city2: String,
        val cod: Int, // 1
        @SerializedName("cod_order_cost")
        val codOrderCost: Int, // 0
        val commission: String,
        @SerializedName("commission_type")
        val commissionType: String,
        @SerializedName("communication_zipcode")
        val communicationZipcode: String,
        val country: String,
        @SerializedName("created_date")
        val createdDate: String, // 2022-08-03T12:46:36.495Z
        @SerializedName("customer_support_city")
        val customerSupportCity: List<Any>,
        @SerializedName("date_of_join")
        val dateOfJoin: Any?, // null
        @SerializedName("date_of_releiving")
        val dateOfReleiving: Any?, // null
        val deleted: Int, // 0
        @SerializedName("delivery_boy")
        val deliveryBoy: List<Any>,
        @SerializedName("delivery_city")
        val deliveryCity: List<Any>,
        @SerializedName("delivery_partner_position")
        val deliveryPartnerPosition: DeliveryPartnerPosition,
        @SerializedName("device_token")
        val deviceToken: String, // c3DfSEbrTwqFmvSg6UIFHJ:APA91bHHeeMW3ceWBKLHeV0krxNVkD_pCqh3NFcdkupZI8v5zpD8bWMyaJrMp85zj0CgQf7NIZXJC-WagpC55n3eSBxzQ2R9pviYAI76oZcjDw0stO-R_m0u04kasuzC8XsoA-UYYYlY
        @SerializedName("device_type")
        val deviceType: String, // Android
        val doc1: String,
        @SerializedName("doc1_type")
        val doc1Type: String,
        val doc2: String,
        @SerializedName("doc2_type")
        val doc2Type: String,
        val doc3: String,
        @SerializedName("doc3_type")
        val doc3Type: String,
        val email: String, // satyajit9830@gmail.com
        @SerializedName("email_otp")
        val emailOtp: Int, // 0
        @SerializedName("father_name")
        val fatherName: String,
        val `file`: List<Any>,
        @SerializedName("first_time")
        val firstTime: Int, // 1
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("health_insurance_number")
        val healthInsuranceNumber: String,
        @SerializedName("_id")
        val id: String, // 62ea6e4089847800094026f6
        val ifsc: Any?, // null
        @SerializedName("login_active")
        val loginActive: Int, // 1
        @SerializedName("lp_manager")
        val lpManager: List<Any>,
        val master: Any?, // null
        val mobile: String, // 7891562370
        @SerializedName("monthly_fixed_cost")
        val monthlyFixedCost: String, // 0
        val note: String,
        val office: Any?, // null
        val otp: Int, // 878333
        val pan: String,
        val password: String,
        @SerializedName("pickup_boy_position")
        val pickupBoyPosition: PickupBoyPosition,
        val post: String,
        @SerializedName("present_address")
        val presentAddress: String,
        @SerializedName("price_per_kg")
        val pricePerKg: String, // 0
        @SerializedName("price_per_pack")
        val pricePerPack: String, // 0
        @SerializedName("profile_image")
        val profileImage: String,
        @SerializedName("rate_per_km")
        val ratePerKm: String, // 0
        @SerializedName("reffer_by")
        val refferBy: String,
        val salary: String,
        @SerializedName("seo_desc")
        val seoDesc: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("service_zipcode")
        val serviceZipcode: String,
        val slug: String,
        val state: String,
        val subscription: Subscription,
        val token1: String,
        val token2: String,
        @SerializedName("update_date")
        val updateDate: String, // 2022-08-03T12:46:36.495Z
        @SerializedName("user_type")
        val userType: String, // customer
        @SerializedName("__v")
        val v: Int, // 0
        @SerializedName("vendor_position")
        val vendorPosition: VendorPosition,
        val zipcode: String
    ) {
        data class CargoPosition(
            val coordinates: List<Int>,
            val type: String // Point
        )

        data class DeliveryPartnerPosition(
            val coordinates: List<Int>,
            val type: String // Point
        )

        data class PickupBoyPosition(
            val coordinates: List<Int>,
            val type: String // Point
        )

        data class Subscription(
            @SerializedName("exp_date")
            val expDate: Any?, // null
            val key: String,
            val plan: Any?, // null
            val point: Int, // 0
            val updated: Any? // null
        )

        data class VendorPosition(
            val coordinates: List<Int>,
            val type: String // Point
        )
    }
}