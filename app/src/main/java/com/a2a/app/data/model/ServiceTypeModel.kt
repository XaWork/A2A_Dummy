package com.a2a.app.data.model

data class ServiceTypeModel(
    val message: String,
    val result: List<Result>,
    val status: String
) {
    data class Result(
        val _id: String,
        val active: Int,
        val created_date: Any,
        val deleted: Int,
        val desc: String,
        val `file`: String,
        val name: String,
        val update_date: String
    )
}