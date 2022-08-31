package com.a2a.app.data.network

import com.a2a.app.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomApi {

    @GET("all-cities")
    suspend fun allCities(): CityModel

    @GET("all-parent-categories")
    suspend fun allCategories(): AllCategoryModel

    @GET("all-sub-categories")
    suspend fun allSubCategories(
        @Query("parent")categoryId: String
    ):AllSubCategoryModel

    @GET("state-list")
    suspend fun allStates(): StateListModel

    @GET("city-list-by-state")
    suspend fun cityByState(
    @Query("state")state: String
    ): CityListModel

    @GET("zipcode-by-city")
    suspend fun zipByCity(
    @Query("city")city: String
    ): ZipListModel

    @GET("cutofftime-check")
    suspend fun checkCutOffTime(
        @Query("start-city")startCity: String,
        @Query("end-city")endCity: String,
    ):CheckCutOffTimeModel
}