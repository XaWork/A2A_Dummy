package com.a2a.app.data.model

data class CheckOrderStatusModel(
    val message: String,
    val result: Result,
    val status: String
) {
    data class Result(
        val __v: Int,
        val _id: String,
        val additional_cost: String,
        val browser: String,
        val bundle_qr: Int,
        val cargo_partner: String,
        val cargo_partner_commission: Int,
        val cargo_position: CargoPosition,
        val category: String,
        val cgst: String,
        val complementary: Int,
        val coupon: Any,
        val couponamount: Any,
        val coupontype: Any,
        val created_date: String,
        val customer_order_status: String,
        val deleted: Int,
        val delivery_boy: Any,
        val delivery_date: String,
        val delivery_image: String,
        val delivery_partner: String,
        val delivery_partner_commission: Int,
        val delivery_partner_position: DeliveryPartnerPosition,
        val delivery_type: String,
        val delivery_video: String,
        val delivery_weight: String,
        val destination_address: String,
        val distance: String,
        val express: String,
        val finalprice: String,
        val gateway: Any,
        val has_note: String,
        val height: String,
        val igst: String,
        val length: String,
        val live_temparature: String,
        val live_tracking: String,
        val lp_head: Any,
        val lp_manager: Any,
        val order_city: Any,
        val orderid: String,
        val otp: String,
        val paid: Int,
        val partial_transaction: String,
        val partial_transaction_amount: String,
        val pickup_address: String,
        val pickup_boy: Any,
        val pickup_image: String,
        val pickup_partner: PickupPartner,
        val pickup_partner_commission: Int,
        val pickup_video: String,
        val pickup_weight: String,
        val picture_recording: String,
        val position: Position,
        val price: String,
        val schedule_date: String,
        val schedule_status: String,
        val schedule_status_to: String,
        val schedule_time: String,
        val sgst: String,
        val status: String,
        val sub_category: String,
        val temparature: String,
        val timeslot: String,
        val totalPackingPrice: String,
        val totalShippingPrice: String,
        val total_weight: String,
        val transactionid: Any,
        val update_date: String,
        val user: String,
        val vendor: Any,
        val vendor_city: Any,
        val video_recording: String,
        val wallet_discount: String,
        val width: String
    ) {
        data class CargoPosition(
            val coordinates: List<Int>,
            val type: String
        )

        data class DeliveryPartnerPosition(
            val coordinates: List<Int>,
            val type: String
        )

        data class PickupPartner(
            val __v: Int,
            val _id: String,
            val aadhar: String,
            val about: String,
            val acc_holder_name: String,
            val acc_number: String,
            val account_statement: String,
            val active: Int,
            val additional_cost1: Int,
            val additional_cost2: Int,
            val address: String,
            val bag_no: List<Any>,
            val bank_address: String,
            val bank_name: String,
            val branch_code: String,
            val cargo_position: CargoPosition,
            val categories: List<Any>,
            val city: Any,
            val city2: String,
            val cod: Any,
            val cod_order_cost: Int,
            val commission: String,
            val commission_type: String,
            val communication_zipcode: String,
            val contract_end: String,
            val contract_start: String,
            val country: String,
            val created_date: String,
            val credit_limit: String,
            val customer_support_city: List<String>,
            val date_of_join: Any,
            val date_of_releiving: Any,
            val deleted: Int,
            val delivery_boy: List<Any>,
            val delivery_city: List<Any>,
            val delivery_partner_position: DeliveryPartnerPosition,
            val device_token: String,
            val device_type: String,
            val doc1: String,
            val doc1_type: String,
            val doc2: String,
            val doc2_type: String,
            val doc3: String,
            val doc3_type: String,
            val dunning_period: String,
            val email: String,
            val email_otp: Int,
            val father_name: String,
            val `file`: List<Any>,
            val first_time: Int,
            val full_name: String,
            val health_insurance_number: String,
            val ifsc: String,
            val insurance_coverage: String,
            val login_active: Int,
            val lp_manager: List<Any>,
            val master: Any,
            val maximum_weight: String,
            val minimum_guranteed: String,
            val mobile: String,
            val monthly_fixed_cost: String,
            val note: String,
            val office: Any,
            val otp: Int,
            val pan: String,
            val password: String,
            val pickup_boy_position: PickupBoyPosition,
            val pickup_time: String,
            val post: String,
            val present_address: String,
            val price_per_kg: String,
            val price_per_pack: String,
            val profile_image: String,
            val rate_per_km: String,
            val reffer_by: String,
            val role: Any,
            val salary: String,
            val seo_desc: String,
            val seo_title: String,
            val service_zipcode: String,
            val slug: String,
            val state: String,
            val subscription: Subscription,
            val token1: String,
            val token2: String,
            val update_date: String,
            val user_type: String,
            val vendor_position: VendorPosition,
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
                val exp_date: Any,
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
    }
}