package com.a2a.app.data.model

import com.google.gson.annotations.SerializedName

data class DriverLocation(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Driver? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Driver(

	@field:SerializedName("lng")
	val lng: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
)