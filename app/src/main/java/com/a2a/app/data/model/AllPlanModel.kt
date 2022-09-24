package com.a2a.app.data.model

data class AllPlanModel(
    val result: List<Result>,
    val status: String
) {
    data class Result(
        val __v: Int,
        val _id: String,
        val active: Int,
        val city: List<City>,
        val created_date: String,
        val day: Int,
        val deleted: Int,
        val description: String,
        val discount: Int,
        val max_price: Int,
        val min_price: Int,
        val name: String,
        val point: Int,
        val price: Float,
        val update_date: String
    ) {
        data class City(
            val _id: String,
            val name: String
        )
    }
}