package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class StateListModel(
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        val city: List<City>,
        val deleted: Int, // 0
        @SerializedName("_id")
        val id: String, // 5f665ee12d45902c98aa8f18
        val name: String // Punjab (PB)
    ) {
        data class City(
            val active: Int, // 1
            val cod: Boolean, // true
            @SerializedName("created_date")
            val createdDate: String, // 2022-04-20T06:23:19.461Z
            val deleted: Int, // 0
            val description: String, // <span style="color: rgb(77, 81, 86); font-family: arial, sans-serif; font-size: 14px;">Mumbai (formerly called Bombay) is a densely populated city on India’s west coast. A financial center, it's India's largest city. On the Mumbai Harbour waterfront stands the iconic Gateway of India stone arch, built by the British Raj in 1924. Offshore, nearby Elephanta Island holds ancient cave temples dedicated to the Hindu god Shiva. The city's also famous as the heart of the Bollywood film industry.</span><span style="color: rgb(77, 81, 86); font-family: arial, sans-serif; font-size: 14px;"><span class="eHaQD" style="color: rgb(112, 117, 122);">&nbsp;</span></span>
            @SerializedName("description_after_content")
            val descriptionAfterContent: String, // Mumbai (formerly called Bombay) is a densely populated city on India’s west coast. A financial center, it's India's largest city. On the Mumbai Harbour waterfront stands the iconic Gateway of India stone arch, built by the British Raj in 1924. Offshore, nearby Elephanta Island holds ancient cave temples dedicated to the Hindu god Shiva. The city's also famous as the heart of the Bollywood film industry. 
            val `file`: String, // https://a2a.sgp1.digitaloceanspaces.com/1650435800307wwhtz.png
            val file2: String, // https://a2a.sgp1.digitaloceanspaces.com/1650435800322pzj5n.png
            val footer: Int, // 0
            @SerializedName("footer_content")
            val footerContent: String, // Mumbai (formerly called Bombay) is a densely populated city on India’s west coast. A financial center, it's India's largest city. On the Mumbai Harbour waterfront stands the iconic Gateway of India stone arch, built by the British Raj in 1924
            val heading: String, // Mumbai 
            @SerializedName("_id")
            val id: String, // 625fa6d89d1cbb00097c505e
            val name: String, // MUMBAI
            val ps: String, // both
            val slug: String, // mumbai
            @SerializedName("slug_history")
            val slugHistory: List<String>,
            val state: String, // 5f665ee12d45902c98aa8f11
            @SerializedName("sub_heading")
            val subHeading: String,
            val timeslots: List<Timeslot>,
            @SerializedName("update_date")
            val updateDate: String, // 2022-04-20T06:23:19.461Z
            @SerializedName("__v")
            val v: Int // 0
        ) {
            data class Timeslot(
                @SerializedName("cost_multiplier")
                val costMultiplier: String, // 2.5
                @SerializedName("end_time")
                val endTime: String, // 18:00
                @SerializedName("start_time")
                val startTime: String // 04:00
            )
        }
    }
}