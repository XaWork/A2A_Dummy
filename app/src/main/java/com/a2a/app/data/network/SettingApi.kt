package com.a2a.app.data.network

import com.a2a.app.data.model.SettingsModel
import retrofit2.http.POST

interface SettingApi {

    @POST("settings")
    suspend fun getSettings(): SettingsModel
}