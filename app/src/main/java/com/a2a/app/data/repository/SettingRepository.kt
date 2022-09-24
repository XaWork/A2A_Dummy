package com.a2a.app.data.repository

import androidx.lifecycle.MutableLiveData
import com.a2a.app.common.BaseRepository
import com.a2a.app.common.Status
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.network.SettingApi
import com.bumptech.glide.load.engine.Resource
import javax.inject.Inject
import javax.inject.Singleton


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
