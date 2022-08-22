package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class AllSubCategoryModel(
    val message: String,
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        @SerializedName("additional_cost")
        val additionalCost: String, // 250
        @SerializedName("created_date")
        val createdDate: String, // 2022-04-19T11:49:39.584Z
        val deleted: Int, // 0
        val description: String, // These products are prepared using advanced vacuum freeze-drying technology which removes water from the food and re-hydrates immediately when you add water again 
        @SerializedName("description_after_content")
        val descriptionAfterContent: String, // These products are prepared using advanced vacuum freeze-drying technology which removes water from the food and re-hydrates immediately when you add water again 
        val `file`: String, // https://a2a.sgp1.digitaloceanspaces.com/1650369034763ie0kj.jpeg
        @SerializedName("_id")
        val id: String, // 625ea20bb469c000098c9680
        val name: String, // Ready-made consumable product
        val parent: String, // 625e5a75647b170009c941d3
        val slug: String, // ready-made-consumable-product
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String, // 2022-04-21T06:58:50.000Z
        @SerializedName("__v")
        val v: Int // 0
    )
}