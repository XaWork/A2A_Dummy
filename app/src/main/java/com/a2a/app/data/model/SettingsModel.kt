package com.a2a.app.data.model

data class SettingsModel(
    val message: String,
    val result: Result,
    val status: String
) {
    data class Result(
        val about_us: String,
        val address: String,
        val afternoon_end: String,
        val app_settings: AppSettings,
        val cancel_time: String,
        val contact_email: String,
        val contact_phone: String,
        val customer_android_version: String,
        val customer_ios_version: String,
        val dashboard: List<Dashboard>,
        val evening_end: String,
        val express_hour: String,
        val logistic_android_version: String,
        val logistic_ios_version: String,
        val maximum_order_value_cod: String,
        val minimum_order_value: String,
        val morning_end: String,
        val night_end: String,
        val normal_hour: String,
        val point: Point,
        val privacy: String,
        val super_fast_hour: String,
        val terms: String,
        val vendor_android_version: String,
        val vendor_ios_version: String,
        val whatsapp: String
    ) {
        data class AppSettings(
            val cancel: Cancel,
            val cod: Cod,
            val delivery_popup: DeliveryPopup,
            val express: Express,
            val header_bg_color: String,
            val order: Order,
            val popup: Popup,
            val slider: Slider
        ) {
            data class Cancel(
                val cancel_popup_bg_color: String,
                val cancel_popup_desctiption: String,
                val cancel_popup_subtitle: String,
                val cancel_popup_title: String,
                val cancel_popup_title_color: String,
                val cancel_subtitle_desctiption_color: String,
                val cancel_subtitle_title_color: String
            )

            data class Cod(
                val cod_popup_bg_color: String,
                val cod_popup_desctiption: String,
                val cod_popup_subtitle: String,
                val cod_popup_title: String,
                val cod_popup_title_color: String,
                val cod_subtitle_desctiption_color: String,
                val cod_subtitle_title_color: String
            )

            data class DeliveryPopup(
                val delivery_popup_bg_color: String,
                val delivery_popup_desctiption: String,
                val delivery_popup_subtitle: String,
                val delivery_popup_title: String,
                val delivery_popup_title_color: String,
                val delivery_subtitle_desctiption_color: String,
                val delivery_subtitle_title_color: String
            )

            data class Express(
                val express_popup_bg_color: String,
                val express_popup_desctiption: String,
                val express_popup_subtitle: String,
                val express_popup_title: String,
                val express_popup_title_color: String,
                val express_subtitle_desctiption_color: String,
                val express_subtitle_title_color: String
            )

            data class Order(
                val order_track_popup_bg_color: String,
                val order_track_popup_desctiption: String,
                val order_track_popup_subtitle: String,
                val order_track_popup_title: String,
                val order_track_popup_title_color: String,
                val order_track_subtitle_desctiption_color: String,
                val order_track_subtitle_title_color: String
            )

            data class Popup(
                val popup_bg_color: String,
                val popup_subtitle: String,
                val popup_title: String,
                val popup_title_color: String,
                val subtitle_desctiption_color: String,
                val subtitle_title_color: String
            )

            data class Slider(
                val service_popup_bg_color: String,
                val service_popup_desctiption: String,
                val service_popup_subtitle: String,
                val service_popup_title: String,
                val service_popup_title_color: String,
                val service_subtitle_desctiption_color: String,
                val service_subtitle_title_color: String
            )
        }

        data class Dashboard(
            val background_image: String,
            val icon: String,
            val title: String
        )

        data class Point(
            val cod_digital_payment: String,
            val point_value_in_rupee: String,
            val review: String,
            val settings1: Settings1,
            val settings2: Settings2,
            val settings3: Settings3,
            val signup_bonus_reciver: String,
            val signup_bonus_sender: String
        ) {
            data class Settings1(
                val max_order: String,
                val min_order: String,
                val point: String
            )

            data class Settings2(
                val max_order: String,
                val min_order: String,
                val point: String
            )

            data class Settings3(
                val max_order: String,
                val min_order: String,
                val point: String
            )
        }
    }
}