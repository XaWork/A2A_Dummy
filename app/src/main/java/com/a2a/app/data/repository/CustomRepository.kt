package com.a2a.app.data.repository

import com.a2a.app.common.BaseRepository
import com.a2a.app.data.network.CustomApi
import javax.inject.Inject

class CustomRepository @Inject constructor(private val customApi: CustomApi): BaseRepository() {

    suspend fun allCities() =
        safeApiCall { customApi.allCities() }

    suspend fun allCategories() =
        safeApiCall { customApi.allCategories() }

    suspend fun allSubCategories(
        categoryId: String
    ) =
        safeApiCall { customApi.allSubCategories(categoryId) }

    suspend fun allStates() =
        safeApiCall { customApi.allStates() }

    suspend fun cityByState(
        state: String
    ) =
        safeApiCall { customApi.cityByState(state) }

    suspend fun zipByCity(
        city: String
    ) =
        safeApiCall { customApi.zipByCity(city) }
}