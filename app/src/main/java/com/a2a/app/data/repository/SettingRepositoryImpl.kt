package com.a2a.app.data.repository

import android.util.Log
import com.a2a.app.common.Status
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.network.SettingApi
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val api: SettingApi
) : SettingRepository {

    override suspend fun getSettings(): Status<SettingsModel> {
        return try {
            val response = api.getSettings()
            Status.Success(response)
        } catch (throwable: Throwable) {
            Log.e("exception", "safeapicall: $throwable")
            when (throwable) {
                is HttpException -> {
                    Status.Failure(
                        false,
                        throwable.code(),
                        throwable.response()?.errorBody().toString()
                    )
                }
                else -> {
                    Status.Failure(true, null, throwable.message.toString())
                }
            }
        }
    }
}