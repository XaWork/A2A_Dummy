package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("cancel_time")
    val cancelTime: String,
    val count: Int,
    val message: String,
    val result: List<Result>,
    @SerializedName("server_time")
    val serverTime: String,
    val status: String
) {
    data class Result(
        @SerializedName("additional_cost")
        val additionalCost: String,
        val browser: String,
        @SerializedName("bundle_qr")
        val bundleQr: Int,
        @SerializedName("cargo_partner")
        val cargoPartner: CargoPartner,
        @SerializedName("cargo_partner_commission")
        val cargoPartnerCommission: Int,
        @SerializedName("cargo_position")
        val cargoPosition: CargoPosition,
        val category: String,
        val cgst: String,
        val complementary: Int,
        val coupon: Any,
        val couponamount: Any,
        val coupontype: Any,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("customer_order_status")
        val customerOrderStatus: String,
        val deleted: Int,
        @SerializedName("delivery_boy")
        val deliveryBoy: Any,
        @SerializedName("delivery_date")
        val deliveryDate: String,
        @SerializedName("delivery_image")
        val deliveryImage: String,
        @SerializedName("delivery_partner")
        val deliveryPartner: DeliveryPartner,
        @SerializedName("delivery_partner_commission")
        val deliveryPartnerCommission: Int,
        @SerializedName("delivery_partner_position")
        val deliveryPartnerPosition: DeliveryPartnerPosition,
        @SerializedName("delivery_type")
        val deliveryType: String,
        @SerializedName("delivery_video")
        val deliveryVideo: String,
        @SerializedName("delivery_weight")
        val deliveryWeight: String,
        @SerializedName("destination_address")
        val destinationAddress: String,
        val distance: String,
        val express: String,
        val finalprice: String,
        val gateway: Any,
        @SerializedName("has_note")
        val hasNote: String,
        val height: String,
        @SerializedName("_id")
        val id: String,
        val igst: String,
        val length: String,
        @SerializedName("live_temparature")
        val liveTemparature: String,
        @SerializedName("live_tracking")
        val liveTracking: String,
        @SerializedName("lp_head")
        val lpHead: Any,
        @SerializedName("lp_manager")
        val lpManager: Any,
        @SerializedName("order_city")
        val orderCity: Any,
        val orderid: String,
        val otp: String,
        val paid: Int,
        @SerializedName("partial_transaction")
        val partialTransaction: String,
        @SerializedName("partial_transaction_amount")
        val partialTransactionAmount: String,
        @SerializedName("pickup_address")
        val pickupAddress: String,
        @SerializedName("pickup_boy")
        val pickupBoy: PickupBoy,
        @SerializedName("pickup_image")
        val pickupImage: String,
        @SerializedName("pickup_partner")
        val pickupPartner: PickupPartner,
        @SerializedName("pickup_partner_commission")
        val pickupPartnerCommission: Int,
        @SerializedName("pickup_video")
        val pickupVideo: String,
        @SerializedName("pickup_weight")
        val pickupWeight: String,
        @SerializedName("picture_recording")
        val pictureRecording: String,
        val position: Position,
        val price: String,
        @SerializedName("schedule_date")
        val scheduleDate: String,
        @SerializedName("schedule_status")
        val scheduleStatus: String,
        @SerializedName("schedule_status_to")
        val scheduleStatusTo: String,
        @SerializedName("schedule_time")
        val scheduleTime: String,
        val sgst: String,
        val status: String,
        @SerializedName("sub_category")
        val subCategory: String,
        val temparature: String,
        val timeslot: String,
        val totalPackingPrice: String,
        val totalShippingPrice: String,
        @SerializedName("total_weight")
        val totalWeight: String,
        val transactionid: Any,
        @SerializedName("update_date")
        val updateDate: String,
        val user: User,
        @SerializedName("__v")
        val v: Int,
        val vendor: Any,
        @SerializedName("vendor_city")
        val vendorCity: Any,
        @SerializedName("video_recording")
        val videoRecording: String,
        @SerializedName("wallet_discount")
        val walletDiscount: String,
        val width: String
    ) {
        data class CargoPartner(
            val aadhar: String,
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: String,
            @SerializedName("acc_number")
            val accNumber: String,
            @SerializedName("account_statement")
            val accountStatement: String,
            val active: Int,
            @SerializedName("additional_cost1")
            val additionalCost1: Int,
            @SerializedName("additional_cost2")
            val additionalCost2: Int,
            val address: String,
            @SerializedName("bag_no")
            val bagNo: List<Any>,
            @SerializedName("bank_address")
            val bankAddress: String,
            @SerializedName("bank_name")
            val bankName: String,
            @SerializedName("branch_code")
            val branchCode: String,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            val categories: List<Any>,
            val city: Any,
            val city2: String,
            val cod: Any,
            @SerializedName("cod_order_cost")
            val codOrderCost: Int,
            val commission: String,
            @SerializedName("commission_type")
            val commissionType: String,
            @SerializedName("communication_zipcode")
            val communicationZipcode: String,
            @SerializedName("contract_end")
            val contractEnd: String,
            @SerializedName("contract_start")
            val contractStart: String,
            val country: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("credit_limit")
            val creditLimit: String,
            @SerializedName("customer_support_city")
            val customerSupportCity: List<String>,
            @SerializedName("date_of_join")
            val dateOfJoin: Any,
            @SerializedName("date_of_releiving")
            val dateOfReleiving: Any,
            val deleted: Int,
            @SerializedName("delivery_boy")
            val deliveryBoy: List<Any>,
            @SerializedName("delivery_city")
            val deliveryCity: List<Any>,
            @SerializedName("delivery_partner_position")
            val deliveryPartnerPosition: DeliveryPartnerPosition,
            @SerializedName("device_token")
            val deviceToken: String,
            @SerializedName("device_type")
            val deviceType: String,
            val doc1: String,
            @SerializedName("doc1_type")
            val doc1Type: String,
            val doc2: String,
            @SerializedName("doc2_type")
            val doc2Type: String,
            val doc3: String,
            @SerializedName("doc3_type")
            val doc3Type: String,
            @SerializedName("dunning_period")
            val dunningPeriod: String,
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            val `file`: List<Any>,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            val ifsc: String,
            @SerializedName("insurance_coverage")
            val insuranceCoverage: String,
            @SerializedName("login_active")
            val loginActive: Int,
            @SerializedName("lp_manager")
            val lpManager: List<Any>,
            val master: Any,
            @SerializedName("maximum_weight")
            val maximumWeight: String,
            @SerializedName("minimum_guranteed")
            val minimumGuranteed: String,
            val mobile: String,
            @SerializedName("monthly_fixed_cost")
            val monthlyFixedCost: String,
            val note: String,
            val office: Any,
            val otp: Int,
            val pan: String,
            val password: String,
            @SerializedName("pickup_boy_position")
            val pickupBoyPosition: PickupBoyPosition,
            @SerializedName("pickup_time")
            val pickupTime: String,
            val post: String,
            @SerializedName("present_address")
            val presentAddress: String,
            @SerializedName("price_per_kg")
            val pricePerKg: String,
            @SerializedName("price_per_pack")
            val pricePerPack: String,
            @SerializedName("profile_image")
            val profileImage: String,
            @SerializedName("rate_per_km")
            val ratePerKm: String,
            @SerializedName("reffer_by")
            val refferBy: String,
            val role: Any,
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
            val updateDate: String,
            @SerializedName("user_type")
            val userType: String,
            @SerializedName("__v")
            val v: Int,
            @SerializedName("vendor_position")
            val vendorPosition: VendorPosition,
            val zipcode: String
        ) {
            data class CargoPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class DeliveryPartnerPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class PickupBoyPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class Subscription(
                @SerializedName("exp_date")
                val expDate: Any,
                val key: String,
                val plan: Any,
                val point: Int,
                val updated: Any
            )

            data class VendorPosition(
                val coordinates: List<Int>,
                val type: String
            )
        }

        data class CargoPosition(
            val coordinates: List<Int>,
            val type: String
        )

        data class DeliveryPartner(
            val aadhar: String,
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: String,
            @SerializedName("acc_number")
            val accNumber: String,
            @SerializedName("account_statement")
            val accountStatement: String,
            val active: Int,
            @SerializedName("additional_cost1")
            val additionalCost1: Int,
            @SerializedName("additional_cost2")
            val additionalCost2: Int,
            val address: String,
            @SerializedName("bag_no")
            val bagNo: List<Any>,
            @SerializedName("bank_address")
            val bankAddress: String,
            @SerializedName("bank_name")
            val bankName: String,
            @SerializedName("branch_code")
            val branchCode: String,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            val categories: List<Any>,
            val city: Any,
            val city2: String,
            val cod: Any,
            @SerializedName("cod_order_cost")
            val codOrderCost: Int,
            val commission: String,
            @SerializedName("commission_type")
            val commissionType: String,
            @SerializedName("communication_zipcode")
            val communicationZipcode: String,
            @SerializedName("contract_end")
            val contractEnd: String,
            @SerializedName("contract_start")
            val contractStart: String,
            val country: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("credit_limit")
            val creditLimit: String,
            @SerializedName("customer_support_city")
            val customerSupportCity: List<String>,
            @SerializedName("date_of_join")
            val dateOfJoin: Any,
            @SerializedName("date_of_releiving")
            val dateOfReleiving: Any,
            val deleted: Int,
            @SerializedName("delivery_boy")
            val deliveryBoy: List<Any>,
            @SerializedName("delivery_city")
            val deliveryCity: List<Any>,
            @SerializedName("delivery_partner_position")
            val deliveryPartnerPosition: DeliveryPartnerPosition,
            @SerializedName("device_token")
            val deviceToken: String,
            @SerializedName("device_type")
            val deviceType: String,
            val doc1: String,
            @SerializedName("doc1_type")
            val doc1Type: String,
            val doc2: String,
            @SerializedName("doc2_type")
            val doc2Type: String,
            val doc3: String,
            @SerializedName("doc3_type")
            val doc3Type: String,
            @SerializedName("dunning_period")
            val dunningPeriod: String,
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            val `file`: List<Any>,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            val ifsc: String,
            @SerializedName("insurance_coverage")
            val insuranceCoverage: String,
            @SerializedName("login_active")
            val loginActive: Int,
            @SerializedName("lp_manager")
            val lpManager: List<Any>,
            val master: Any,
            @SerializedName("maximum_weight")
            val maximumWeight: String,
            @SerializedName("minimum_guranteed")
            val minimumGuranteed: String,
            val mobile: String,
            @SerializedName("monthly_fixed_cost")
            val monthlyFixedCost: String,
            val note: String,
            val office: Any,
            val otp: Int,
            val pan: String,
            val password: String,
            @SerializedName("pickup_boy_position")
            val pickupBoyPosition: PickupBoyPosition,
            @SerializedName("pickup_time")
            val pickupTime: String,
            val post: String,
            @SerializedName("present_address")
            val presentAddress: String,
            @SerializedName("price_per_kg")
            val pricePerKg: String,
            @SerializedName("price_per_pack")
            val pricePerPack: String,
            @SerializedName("profile_image")
            val profileImage: String,
            @SerializedName("rate_per_km")
            val ratePerKm: String,
            @SerializedName("reffer_by")
            val refferBy: String,
            val role: Any,
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
            val updateDate: String,
            @SerializedName("user_type")
            val userType: String,
            @SerializedName("__v")
            val v: Int,
            @SerializedName("vendor_position")
            val vendorPosition: VendorPosition,
            val zipcode: String
        ) {
            data class CargoPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class DeliveryPartnerPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class PickupBoyPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class Subscription(
                @SerializedName("exp_date")
                val expDate: Any,
                val key: String,
                val plan: Any,
                val point: Int,
                val updated: Any
            )

            data class VendorPosition(
                val coordinates: List<Int>,
                val type: String
            )
        }

        data class DeliveryPartnerPosition(
            val coordinates: List<Int>,
            val type: String
        )

        data class PickupBoy(
            val aadhar: String,
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: Any,
            @SerializedName("acc_number")
            val accNumber: Any,
            @SerializedName("account_statement")
            val accountStatement: String,
            val active: Int,
            @SerializedName("additional_cost1")
            val additionalCost1: Int,
            @SerializedName("additional_cost2")
            val additionalCost2: Int,
            val address: String,
            @SerializedName("bag_no")
            val bagNo: List<Any>,
            @SerializedName("bank_address")
            val bankAddress: Any,
            @SerializedName("bank_name")
            val bankName: Any,
            @SerializedName("branch_code")
            val branchCode: Any,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            val categories: List<Any>,
            val city: String,
            val city2: String,
            val cod: Int,
            @SerializedName("cod_order_cost")
            val codOrderCost: Int,
            val commission: String,
            @SerializedName("commission_type")
            val commissionType: String,
            @SerializedName("communication_zipcode")
            val communicationZipcode: String,
            @SerializedName("contract_end")
            val contractEnd: String,
            @SerializedName("contract_start")
            val contractStart: String,
            val country: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("credit_limit")
            val creditLimit: String,
            @SerializedName("customer_support_city")
            val customerSupportCity: List<Any>,
            @SerializedName("date_of_join")
            val dateOfJoin: Any,
            @SerializedName("date_of_releiving")
            val dateOfReleiving: Any,
            val deleted: Int,
            @SerializedName("delivery_boy")
            val deliveryBoy: List<Any>,
            @SerializedName("delivery_city")
            val deliveryCity: List<Any>,
            @SerializedName("delivery_partner_position")
            val deliveryPartnerPosition: DeliveryPartnerPosition,
            @SerializedName("device_token")
            val deviceToken: String,
            @SerializedName("device_type")
            val deviceType: String,
            val doc1: String,
            @SerializedName("doc1_type")
            val doc1Type: String,
            val doc2: String,
            @SerializedName("doc2_type")
            val doc2Type: String,
            val doc3: String,
            @SerializedName("doc3_type")
            val doc3Type: String,
            @SerializedName("dunning_period")
            val dunningPeriod: String,
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            val `file`: List<Any>,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            val ifsc: Any,
            @SerializedName("insurance_coverage")
            val insuranceCoverage: String,
            @SerializedName("login_active")
            val loginActive: Int,
            @SerializedName("lp_manager")
            val lpManager: List<Any>,
            val master: String,
            @SerializedName("maximum_weight")
            val maximumWeight: String,
            @SerializedName("minimum_guranteed")
            val minimumGuranteed: String,
            val mobile: String,
            @SerializedName("monthly_fixed_cost")
            val monthlyFixedCost: String,
            val note: String,
            val office: Any,
            val otp: Int,
            val pan: String,
            val password: String,
            @SerializedName("pickup_boy_position")
            val pickupBoyPosition: PickupBoyPosition,
            @SerializedName("pickup_time")
            val pickupTime: String,
            val post: String,
            @SerializedName("present_address")
            val presentAddress: String,
            @SerializedName("price_per_kg")
            val pricePerKg: String,
            @SerializedName("price_per_pack")
            val pricePerPack: String,
            @SerializedName("profile_image")
            val profileImage: Any,
            @SerializedName("rate_per_km")
            val ratePerKm: String,
            @SerializedName("reffer_by")
            val refferBy: String,
            val role: Any,
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
            val updateDate: String,
            @SerializedName("user_type")
            val userType: String,
            @SerializedName("__v")
            val v: Int,
            @SerializedName("vendor_position")
            val vendorPosition: VendorPosition,
            val zipcode: String
        ) {
            data class CargoPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class DeliveryPartnerPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class PickupBoyPosition(
                val coordinates: List<Double>,
                val type: String
            )

            data class Subscription(
                @SerializedName("exp_date")
                val expDate: Any,
                val key: String,
                val plan: Any,
                val point: Int,
                val updated: Any
            )

            data class VendorPosition(
                val coordinates: List<Int>,
                val type: String
            )
        }

        data class PickupPartner(
            val aadhar: String,
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: String,
            @SerializedName("acc_number")
            val accNumber: String,
            @SerializedName("account_statement")
            val accountStatement: String,
            val active: Int,
            @SerializedName("additional_cost1")
            val additionalCost1: Int,
            @SerializedName("additional_cost2")
            val additionalCost2: Int,
            val address: String,
            @SerializedName("bag_no")
            val bagNo: List<Any>,
            @SerializedName("bank_address")
            val bankAddress: String,
            @SerializedName("bank_name")
            val bankName: String,
            @SerializedName("branch_code")
            val branchCode: String,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            val categories: List<Any>,
            val city: Any,
            val city2: String,
            val cod: Any,
            @SerializedName("cod_order_cost")
            val codOrderCost: Int,
            val commission: String,
            @SerializedName("commission_type")
            val commissionType: String,
            @SerializedName("communication_zipcode")
            val communicationZipcode: String,
            @SerializedName("contract_end")
            val contractEnd: String,
            @SerializedName("contract_start")
            val contractStart: String,
            val country: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("credit_limit")
            val creditLimit: String,
            @SerializedName("customer_support_city")
            val customerSupportCity: List<String>,
            @SerializedName("date_of_join")
            val dateOfJoin: Any,
            @SerializedName("date_of_releiving")
            val dateOfReleiving: Any,
            val deleted: Int,
            @SerializedName("delivery_boy")
            val deliveryBoy: List<Any>,
            @SerializedName("delivery_city")
            val deliveryCity: List<Any>,
            @SerializedName("delivery_partner_position")
            val deliveryPartnerPosition: DeliveryPartnerPosition,
            @SerializedName("device_token")
            val deviceToken: String,
            @SerializedName("device_type")
            val deviceType: String,
            val doc1: String,
            @SerializedName("doc1_type")
            val doc1Type: String,
            val doc2: String,
            @SerializedName("doc2_type")
            val doc2Type: String,
            val doc3: String,
            @SerializedName("doc3_type")
            val doc3Type: String,
            @SerializedName("dunning_period")
            val dunningPeriod: String,
            val email: String,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            val `file`: List<Any>,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            val ifsc: String,
            @SerializedName("insurance_coverage")
            val insuranceCoverage: String,
            @SerializedName("login_active")
            val loginActive: Int,
            @SerializedName("lp_manager")
            val lpManager: List<Any>,
            val master: Any,
            @SerializedName("maximum_weight")
            val maximumWeight: String,
            @SerializedName("minimum_guranteed")
            val minimumGuranteed: String,
            val mobile: String,
            @SerializedName("monthly_fixed_cost")
            val monthlyFixedCost: String,
            val note: String,
            val office: Any,
            val otp: Int,
            val pan: String,
            val password: String,
            @SerializedName("pickup_boy_position")
            val pickupBoyPosition: PickupBoyPosition,
            @SerializedName("pickup_time")
            val pickupTime: String,
            val post: String,
            @SerializedName("present_address")
            val presentAddress: String,
            @SerializedName("price_per_kg")
            val pricePerKg: String,
            @SerializedName("price_per_pack")
            val pricePerPack: String,
            @SerializedName("profile_image")
            val profileImage: String,
            @SerializedName("rate_per_km")
            val ratePerKm: String,
            @SerializedName("reffer_by")
            val refferBy: String,
            val role: Any,
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
            val updateDate: String,
            @SerializedName("user_type")
            val userType: String,
            @SerializedName("__v")
            val v: Int,
            @SerializedName("vendor_position")
            val vendorPosition: VendorPosition,
            val zipcode: String
        ) {
            data class CargoPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class DeliveryPartnerPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class PickupBoyPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class Subscription(
                @SerializedName("exp_date")
                val expDate: Any,
                val key: String,
                val plan: Any,
                val point: Int,
                val updated: Any
            )

            data class VendorPosition(
                val coordinates: List<Int>,
                val type: String
            )
        }

        data class Position(
            val coordinates: List<Int>,
            val type: String
        )

        data class User(
            val aadhar: String,
            val about: String,
            @SerializedName("acc_holder_name")
            val accHolderName: Any,
            @SerializedName("acc_number")
            val accNumber: Any,
            @SerializedName("account_statement")
            val accountStatement: String,
            val active: Int,
            @SerializedName("additional_cost1")
            val additionalCost1: Int,
            @SerializedName("additional_cost2")
            val additionalCost2: Int,
            val address: String,
            @SerializedName("bag_no")
            val bagNo: List<Any>,
            @SerializedName("bank_address")
            val bankAddress: Any,
            @SerializedName("bank_name")
            val bankName: Any,
            @SerializedName("branch_code")
            val branchCode: Any,
            @SerializedName("cargo_position")
            val cargoPosition: CargoPosition,
            val categories: List<Any>,
            val city: Any,
            val city2: String,
            val cod: Int,
            @SerializedName("cod_order_cost")
            val codOrderCost: Int,
            val commission: String,
            @SerializedName("commission_type")
            val commissionType: String,
            @SerializedName("communication_zipcode")
            val communicationZipcode: String,
            @SerializedName("contract_end")
            val contractEnd: String,
            @SerializedName("contract_start")
            val contractStart: String,
            val country: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("credit_limit")
            val creditLimit: String,
            @SerializedName("customer_support_city")
            val customerSupportCity: List<Any>,
            @SerializedName("date_of_join")
            val dateOfJoin: Any,
            @SerializedName("date_of_releiving")
            val dateOfReleiving: Any,
            val deleted: Int,
            @SerializedName("delivery_boy")
            val deliveryBoy: List<Any>,
            @SerializedName("delivery_city")
            val deliveryCity: List<Any>,
            @SerializedName("delivery_partner_position")
            val deliveryPartnerPosition: DeliveryPartnerPosition,
            @SerializedName("device_token")
            val deviceToken: String,
            @SerializedName("device_type")
            val deviceType: String,
            val doc1: String,
            @SerializedName("doc1_type")
            val doc1Type: String,
            val doc2: String,
            @SerializedName("doc2_type")
            val doc2Type: String,
            val doc3: String,
            @SerializedName("doc3_type")
            val doc3Type: String,
            @SerializedName("dunning_period")
            val dunningPeriod: String,
            val email: Any,
            @SerializedName("email_otp")
            val emailOtp: Int,
            @SerializedName("father_name")
            val fatherName: String,
            val `file`: List<Any>,
            @SerializedName("first_time")
            val firstTime: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("health_insurance_number")
            val healthInsuranceNumber: String,
            @SerializedName("_id")
            val id: String,
            val ifsc: Any,
            @SerializedName("insurance_coverage")
            val insuranceCoverage: String,
            @SerializedName("login_active")
            val loginActive: Int,
            @SerializedName("lp_manager")
            val lpManager: List<Any>,
            val master: Any,
            @SerializedName("maximum_weight")
            val maximumWeight: String,
            @SerializedName("minimum_guranteed")
            val minimumGuranteed: String,
            val mobile: String,
            @SerializedName("monthly_fixed_cost")
            val monthlyFixedCost: String,
            val note: String,
            val office: Any,
            val otp: Int,
            val pan: String,
            val password: String,
            @SerializedName("pickup_boy_position")
            val pickupBoyPosition: PickupBoyPosition,
            @SerializedName("pickup_time")
            val pickupTime: String,
            val post: String,
            @SerializedName("present_address")
            val presentAddress: String,
            @SerializedName("price_per_kg")
            val pricePerKg: String,
            @SerializedName("price_per_pack")
            val pricePerPack: String,
            @SerializedName("profile_image")
            val profileImage: String,
            @SerializedName("rate_per_km")
            val ratePerKm: String,
            @SerializedName("reffer_by")
            val refferBy: String,
            val role: Any,
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
            val token1: Any,
            val token2: String,
            @SerializedName("update_date")
            val updateDate: String,
            @SerializedName("user_type")
            val userType: String,
            @SerializedName("__v")
            val v: Int,
            @SerializedName("vendor_position")
            val vendorPosition: VendorPosition,
            val zipcode: String
        ) {
            data class CargoPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class DeliveryPartnerPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class PickupBoyPosition(
                val coordinates: List<Int>,
                val type: String
            )

            data class Subscription(
                @SerializedName("exp_date")
                val expDate: String,
                val key: Any,
                val plan: String,
                val point: Int,
                val updated: String
            )

            data class VendorPosition(
                val coordinates: List<Int>,
                val type: String
            )
        }
    }
}