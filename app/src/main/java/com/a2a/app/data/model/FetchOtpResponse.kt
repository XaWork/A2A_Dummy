package com.a2a.app.data.model

import androidx.annotation.Keep

@Keep
data class FetchOtpResponse(
    val status: String,
    val data: OtpDetails? = null,
    val message: String? = null
)
@Keep
data class OtpDetails(
    val details: String? = null,
    val status: String? = null
)
