package com.a2a.app.data.repository

import android.util.Log
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.network.CustomApi
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomRepositoryImpl @Inject constructor(private val api: CustomApi) : CustomRepository {

    override suspend fun home(): Status<HomeModel> {
        return try {
            val response = api.home()
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

    override suspend fun allPlans(): Status<AllPlanModel> {
        return try {
            val response = api.allPlans()
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

    override suspend fun serviceTypes(): Status<ServiceTypeModel> {
        return try {
            val response = api.serviceTypes()
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

    override suspend fun allCities(): Status<CityModel> {
        return try {
            val response = api.allCities()
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

    override suspend fun allCategories(): Status<AllCategoryModel> {
        return try {
            val response = api.allCategories()
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

    override suspend fun allSubCategories(categoryId: String): Status<AllSubCategoryModel> {
        return try {
            val response = api.allSubCategories(categoryId)
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

    override suspend fun allStates(): Status<StateListModel> {
        return try {
            val response = api.allStates()
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

    override suspend fun cityByState(state: String): Status<CityListModel> {
        return try {
            val response = api.cityByState(state)
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

    override suspend fun zipByCity(city: String): Status<ZipListModel> {
        return try {
            val response = api.zipByCity(city)
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

    override suspend fun checkCutOffTime(
        startCity: String,
        endCity: String
    ): CheckCutOffTimeModel {
       return api.checkCutOffTime(startCity, endCity)
    }

    override suspend fun offerDeal(): Status<OfferDealModel> {
        return try {
            val response = api.offerDeal()
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

    override suspend fun getAllCategories(): AllCategoryModel{
        return api.allCategories()
    }

    override suspend fun getAllSubCategories(categoryId: String): AllSubCategoryModel {
        return api.allSubCategories(categoryId)
    }

    override suspend fun normalTimeSlots(
        scheduleDate: String,
        destinationAddress: String,
        pickupAddress: String
    ): NormalTimeslotModel {
        return api.normalTimeslots(scheduleDate, destinationAddress, pickupAddress)
    }

    override suspend fun getAllServices(): ServiceTypeModel {
        return api.serviceTypes()
    }
}