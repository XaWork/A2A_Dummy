package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class AllCategoryModel(
    val message: String,
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        @SerializedName("additional_cost")
        val additionalCost: String, // 0
        @SerializedName("created_date")
        val createdDate: String, // 2022-04-19T06:43:15.814Z
        val deleted: Int, // 0
        val description: String, // FROZEN PRODUCTS TRANSPORTATION
        @SerializedName("description_after_content")
        val descriptionAfterContent: String, // FROZEN PRODUCTS TRANSPORTATION
        val `file`: String, // https://a2a.sgp1.digitaloceanspaces.com/1650350708571p7nap.jpeg
        @SerializedName("_id")
        val id: String, // 625e5a75647b170009c941d3
        val name: String, // FROZEN
        val parent: Any?, // null
        val slug: String, // frozen
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String, // 2022-04-19T06:43:15.814Z
        @SerializedName("__v")
        val v: Int // 0
    )
}