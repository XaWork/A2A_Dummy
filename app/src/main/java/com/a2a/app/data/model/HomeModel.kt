package com.a2a.app.data.model

data class HomeModel(
    val message: String,
    val result: Result,
    val status: String
) {
    data class Result(
        val blogs: List<Blog>,
        val city: List<City>,
        val clients: List<Client>,
        val slider: List<Slider>,
        val testimonials: List<Testimonial>
    ) {
        data class Blog(
            val __v: Int,
            val _id: String,
            val active: Int,
            val categories: List<Any>,
            val createdAt: String,
            val description: String,
            val image: String,
            val seo_description: String,
            val seo_keywords: String,
            val seo_title: String,
            val slug: String,
            val slug_history: List<String>,
            val title: String,
            val updatedAt: String
        )

        data class City(
            val __v: Int,
            val _id: String,
            val active: Int,
            val cod: Boolean,
            val created_date: String,
            val deleted: Int,
            val description: String,
            val description_after_content: String,
            val `file`: String,
            val file2: String,
            val footer: Int,
            val footer_content: String,
            val heading: String,
            val name: String,
            val ps: String,
            val slug: String,
            val slug_history: List<String>,
            val state: String,
            val sub_heading: String,
            val timeslots: List<Timeslot>,
            val update_date: String
        ) {
            data class Timeslot(
                val cost_multiplier: String,
                val end_time: String,
                val start_time: String
            )
        }

        data class Client(
            val __v: Int,
            val _id: String,
            val active: Int,
            val created_date: String,
            val deleted: Int,
            val `file`: String,
            val name: String,
            val update_date: String
        )

        data class Slider(
            val __v: Int,
            val _id: String,
            val active: Int,
            val city: String,
            val created_date: String,
            val deleted: Int,
            val `file`: String,
            val name: String,
            val update_date: String
        )

        data class Testimonial(
            val __v: Int,
            val _id: String,
            val createdAt: String,
            val image: String,
            val message: String,
            val name: String,
            val updatedAt: String
        )
    }
}