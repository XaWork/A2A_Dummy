package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class CityModel(
    val message: String,
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        val cod: Boolean, // true
        @SerializedName("created_date")
        val createdDate: String, // 2022-04-20T08:09:54.767Z
        val deleted: Int, // 0
        val description: String, // <span style="color: rgb(77, 81, 86); font-family: arial, sans-serif; font-size: 14px;">Lucknow, a large city in northern India, is the capital of the state of Uttar Pradesh. Toward its center is Rumi Darwaza, a Mughal gateway. Nearby, the 18th-century Bara Imambara shrine has a huge arched hall. Upstairs, Bhool Bhulaiya is a maze of narrow tunnels with city views from its upper balconies. Close by, the grand Victorian Husainabad Clock Tower was built as a victory column in 1881.</span><span style="color: rgb(77, 81, 86); font-family: arial, sans-serif; font-size: 14px;"><span class="eHaQD" style="color: rgb(112, 117, 122);">&nbsp;</span></span>
        @SerializedName("description_after_content")
        val descriptionAfterContent: String, // Lucknow, a large city in northern India, is the capital of the state of Uttar Pradesh. Toward its center is Rumi Darwaza, a Mughal gateway. Nearby, the 18th-century Bara Imambara shrine has a huge arched hall. Upstairs, Bhool Bhulaiya is a maze of narrow tunnels with city views from its upper balconies. Close by, the grand Victorian Husainabad Clock Tower was built as a victory column in 1881. 
        val `file`: String, // https://a2a.sgp1.digitaloceanspaces.com/16504421969050ipxb.png
        val file2: String, // https://a2a.sgp1.digitaloceanspaces.com/1650442197088mwkt1.png
        val footer: Int, // 0
        @SerializedName("footer_content")
        val footerContent: String, // Lucknow, a large city in northern India, is the capital of the state of Uttar Pradesh. Toward its center is Rumi Darwaza, a Mughal gateway. Nearby, the 18th-century Bara Imambara shrine has a huge arched hall. Upstairs, Bhool Bhulaiya is a maze of narrow tunnels with city views from its upper balconies. Close by, the grand Victorian Husainabad Clock Tower was built as a victory column in 1881. 
        val heading: String, // LUCKNOW
        @SerializedName("_id")
        val id: String, // 625fbfd60614490009f575f4
        val name: String, // LUCKNOW
        val ps: String, // both
        val slug: String, // lucknow
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        val state: String, // 5f665ee12d45902c98aa8f1d
        @SerializedName("sub_heading")
        val subHeading: String, // City of Nawab - Lucknow
        val timeslots: List<Timeslot>,
        @SerializedName("update_date")
        val updateDate: String, // 2022-04-20T08:09:54.767Z
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