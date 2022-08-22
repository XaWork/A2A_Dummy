package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class CityListModel(
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        val cod: Boolean, // true
        @SerializedName("created_date")
        val createdDate: String, // 2021-09-26T15:46:29.357Z
        val deleted: Int, // 0
        val description: String, // <span style="color: rgb(77, 81, 86); font-family: arial, sans-serif; font-size: 14px;">Delhi, India’s capital territory, is a massive metropolitan area in the country’s north. In Old Delhi, a neighborhood dating to the 1600s, stands the imposing Mughal-era Red Fort, a symbol of India, and the sprawling Jama Masjid mosque, whose courtyard accommodates 25,000 people. Nearby is Chandni Chowk, a vibrant bazaar filled with food carts, sweets shops and spice stalls.</span>
        @SerializedName("description_after_content")
        val descriptionAfterContent: String, // Delhi, India’s capital territory, is a massive metropolitan area in the country’s north. In Old Delhi, a neighborhood dating to the 1600s, stands the imposing Mughal-era Red Fort, a symbol of India, and the sprawling Jama Masjid mosque, whose courtyard accommodates 25,000 people. Nearby is Chandni Chowk, a vibrant bazaar filled with food carts, sweets shops and spice stalls.
        val `file`: String, // https://a2a.sgp1.digitaloceanspaces.com/16504378328698ling.png
        val file2: String, // https://a2a.sgp1.digitaloceanspaces.com/1650437832983eptnz.png
        val footer: Int, // 0
        @SerializedName("footer_content")
        val footerContent: String, // Delhi, India’s capital territory, is a massive metropolitan area in the country’s north. In Old Delhi, a neighborhood dating to the 1600s, stands the imposing Mughal-era Red Fort, a symbol of India, and the sprawling Jama Masjid mosque, whose courtyard accommodates 25,000 people. Nearby is Chandni Chowk, a vibrant bazaar filled with food carts, sweets shops and spice stalls.
        val heading: String, // DELHI
        @SerializedName("_id")
        val id: String, // 615097ec4426a70009eb380f
        val name: String, // DELHI
        val ps: String, // both
        val slug: String, // delhi
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        val state: String, // 5f665ee12d45902c98aa8f06
        @SerializedName("sub_heading")
        val subHeading: String, // Capital of India - Delhi
        val timeslots: List<Timeslot>,
        @SerializedName("update_date")
        val updateDate: String, // 2022-04-20T06:57:13.000Z
        @SerializedName("__v")
        val v: Int // 0
    ) {
        data class Timeslot(
            @SerializedName("cost_multiplier")
            val costMultiplier: String, // 1.0
            @SerializedName("end_time")
            val endTime: String, // 10:00
            @SerializedName("start_time")
            val startTime: String // 08:00
        )
    }
}