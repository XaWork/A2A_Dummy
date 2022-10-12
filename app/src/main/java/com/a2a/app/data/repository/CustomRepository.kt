package com.a2a.app.data.repository

import com.a2a.app.common.Status
import com.a2a.app.data.model.*

interface CustomRepository {

    suspend fun home(): Status<HomeModel>

    suspend fun allPlans(): Status<AllPlanModel>

    suspend fun serviceTypes(): Status<ServiceTypeModel>

    suspend fun allCities(): Status<CityModel>


    suspend fun allCategories(): Status<AllCategoryModel>


    suspend fun allSubCategories(
        categoryId: String
    ): Status<AllSubCategoryModel>

    suspend fun allStates(): Status<StateListModel>


    suspend fun cityByState(
        state: String
    ): Status<CityListModel>

    suspend fun zipByCity(
        city: String
    ): Status<ZipListModel>

    suspend fun checkCutOffTime(
        startCity: String,
        endCity: String,
    ): Status<CheckCutOffTimeModel>

    suspend fun offerDeal(): Status<OfferDealModel>
}