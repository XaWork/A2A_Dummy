package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("cancel_time")
    val cancelTime: String,
    val count: Int, // 2
    val message: String,
    val result: List<Result>,
    @SerializedName("server_time")
    val serverTime: String, // 2022-08-03T11:00:54.406Z
    val status: String // success
) {
    data class Result(
        @SerializedName("additional_cost")
        val additionalCost: String,
        @SerializedName("bundle_qr")
        val bundleQr: Int, // 0
        @SerializedName("cargo_partner")
        val cargoPartner: Any?, // null
        @SerializedName("cargo_partner_commission")
        val cargoPartnerCommission: String, // 0
        @SerializedName("cargo_position")
        val cargoPosition: CargoPosition,
        val category: String, // 61ae1a48630eda3314316173
        val coupon: Any?, // null
        val couponamount: Any?, // null
        val coupontype: Any?, // null
        @SerializedName("created_date")
        val createdDate: String, // 2021-12-23T18:23:45.398Z
        @SerializedName("customer_order_status")
        val customerOrderStatus: String,
        val deleted: Int, // 0
        @SerializedName("delivery_boy")
        val deliveryBoy: Any?, // null
        @SerializedName("delivery_date")
        val deliveryDate: String, // 2021-12-23T18:23:45.398Z
        @SerializedName("delivery_partner")
        val deliveryPartner: Any?, // null
        @SerializedName("delivery_partner_commission")
        val deliveryPartnerCommission: String, // 0
        @SerializedName("delivery_partner_position")
        val deliveryPartnerPosition: DeliveryPartnerPosition,
        @SerializedName("delivery_type")
        val deliveryType: String, // Express
        @SerializedName("delivery_weight")
        val deliveryWeight: String,
        @SerializedName("destination_address")
        val destinationAddress: String, // 61bb41144b49d300090d036e
        val distance: String, // 0
        val express: String, // Express
        val finalprice: String, // 0.0
        val gateway: Any?, // null
        @SerializedName("has_note")
        val hasNote: String, // 0
        val height: String, // 2
        @SerializedName("_id")
        val id: String, // 61c4bf33db1b830009cc8ddf
        val length: String, // 5
        @SerializedName("lp_head")
        val lpHead: Any?, // null
        @SerializedName("lp_manager")
        val lpManager: Any?, // null
        @SerializedName("order_city")
        val orderCity: Any?, // null
        val orderid: String, // T2P-20211148381
        val otp: String,
        val paid: Int, // 0
        @SerializedName("pickup_address")
        val pickupAddress: String, // 61c0923c97431b000963b13d
        @SerializedName("pickup_boy")
        val pickupBoy: Any?, // null
        @SerializedName("pickup_partner")
        val pickupPartner: Any?, // null
        @SerializedName("pickup_partner_commission")
        val pickupPartnerCommission: Int, // 0
        @SerializedName("pickup_weight")
        val pickupWeight: String,
        val position: Position,
        val price: String, // 0.00
        @SerializedName("schedule_date")
        val scheduleDate: String, // 2021-12-23T18:23:45.398Z
        @SerializedName("schedule_status")
        val scheduleStatus: String,
        @SerializedName("schedule_status_to")
        val scheduleStatusTo: String,
        @SerializedName("schedule_time")
        val scheduleTime: String,
        val status: String, // pending_payment
        @SerializedName("sub_category")
        val subCategory: String, // 61ae1a5c630eda331431617c
        val timeslot: String,
        val totalCGST: String?,
        val totalIGST: String?,
        val totalPackingPrice: String?,
        val totalSGST: String?,
        val totalShippingPrice: String?,
        @SerializedName("total_weight")
        val totalWeight: String, // 25
        val transactionid: Any?, // null
        @SerializedName("update_date")
        val updateDate: String, // 2021-12-23T18:23:45.398Z
        val user: User,
        @SerializedName("__v")
        val v: Int, // 0
        val vendor: Any?, // null
        @SerializedName("vendor_city")
        val vendorCity: Any?, // null
        @SerializedName("wallet_discount")
        val walletDiscount: String, // 0
        val width: String // 2
    ) {
        data class CargoPosition(
            val coordinates: List<Int>,
            val type: String // Point
        )

        data class DeliveryPartnerPosition(
            val coordinates: List<Int>,
            val type: String // Point
        )

        data class Position(
            val coordinates: List<Int>,
            val type: String // Point
        )

        data class User(
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
            val createdDate: String, // 2021-10-12T15:56:00.977Z
            @SerializedName("customer_support_city")
            val customerSupportCity: List<Any>,
            @SerializedName("date_of_join")
            val dateOfJoin: Any?, // null
            @SerializedName("date_of_releiving")
            val dateOfReleiving: Any?, // null
            val deleted: Int, // 1
            @SerializedName("delivery_boy")
            val deliveryBoy: List<Any>,
            @SerializedName("delivery_city")
            val deliveryCity: List<Any>,
            @SerializedName("delivery_partner_position")
            val deliveryPartnerPosition: DeliveryPartnerPosition,
            @SerializedName("device_token")
            val deviceToken: String, // dRcPVRqsSomqvAgU_AqeSE:APA91bFoBXG3mp3VhLgYgxROJhKxJSCODtNs14CBtuVYLQlsWVVL1phQ5FR0cFRQz8La0MMYXaicLjtaBHU7Be5bstpPLPpsjOyc9dWwwGtY58-cvmP_nUFVp4rVIKNpJ9c4KbaLttEl
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
            val email: String, // carotkut12@gmail.com
            @SerializedName("email_otp")
            val emailOtp: Int, // 0
            @SerializedName("father_name")
            val fatherName: String,
            val `file`: List<Any>,
            @SerializedName("first_time")
            val firstTime: Int, // 1
            @SerializedName("full_name")
            val fullName: String, // sample
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String, // 6165b028ba218d00098d3768
            val ifsc: Any?, // null
            @SerializedName("login_active")
            val loginActive: Int, // 1
            @SerializedName("lp_manager")
            val lpManager: List<Any>,
            val master: Any?, // null
            val mobile: String, // 8233669973
            @SerializedName("monthly_fixed_cost")
            val monthlyFixedCost: String, // 0
            val note: String,
            val office: Any?, // null
            val otp: Any?, // null
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
            val token1: String, // dRcPVRqsSomqvAgU_AqeSE:APA91bFoBXG3mp3VhLgYgxROJhKxJSCODtNs14CBtuVYLQlsWVVL1phQ5FR0cFRQz8La0MMYXaicLjtaBHU7Be5bstpPLPpsjOyc9dWwwGtY58-cvmP_nUFVp4rVIKNpJ9c4KbaLttEl
            val token2: String, // d4f60000-6fdb-11ec-84fc-1e8d3fde744b
            @SerializedName("update_date")
            val updateDate: String, // 2021-10-12T15:56:00.977Z
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
}


fun OrderModel.Result.tCgst() = if(totalCGST.isNullOrEmpty()) "0" else totalCGST
fun OrderModel.Result.tIGST() = if(totalIGST.isNullOrEmpty()) "0" else totalIGST
fun OrderModel.Result.tSGST() = if(totalSGST.isNullOrEmpty()) "0" else totalSGST