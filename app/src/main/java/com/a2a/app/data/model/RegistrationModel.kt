package com.a2a.app.data.model


import com.google.gson.annotations.SerializedName

data class RegistrationModel(
    val message: String, // OTP has been sent to mobile number.
    @SerializedName("OTP")
    val oTP: String, // 776984
    val status: String // success
)