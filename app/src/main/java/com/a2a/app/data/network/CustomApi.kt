package com.a2a.app.data.network

import com.a2a.app.data.model.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomApi {

    @GET("home-all")
    suspend fun home(): HomeModel

    @GET("all-plan-list")
    suspend fun allPlans():AllPlanModel

    @GET("all-cities")
    suspend fun allCities(): CityModel

    @GET("all-parent-categories")
    suspend fun allCategories(): AllCategoryModel

    @GET("all-sub-categories")
    suspend fun allSubCategories(
        @Query("parent") categoryId: String
    ): AllSubCategoryModel

    @GET("state-list")
    suspend fun allStates(): StateListModel

    @GET("city-list-by-state")
    suspend fun cityByState(
        @Query("state") state: String
    ): CityListModel

    @GET("zipcode-by-city")
    suspend fun zipByCity(
        @Query("city") city: String
    ): ZipListModel

    @GET("cutofftime-check")
    suspend fun checkCutOffTime(
        @Query("start-city") startCity: String,
        @Query("end-city") endCity: String,
    ): CheckCutOffTimeModel

    @GET("offer-deal")
    suspend fun offerDeal(): OfferDealModel

    @GET("service-type")
    suspend fun serviceTypes(): ServiceTypeModel


}