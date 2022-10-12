package com.a2a.app.data.repository

import com.a2a.app.common.Status
import com.a2a.app.data.model.SettingsModel


interface SettingRepository {

    suspend fun getSettings(): Status<SettingsModel>

}


/*@Singleton
class SettingRepositorys @Inject constructor(
    private val api: SettingApi
) : BaseRepository() {

    suspend fun getSettings() =
        safeApiCall { api.getSettings() }
}*/
