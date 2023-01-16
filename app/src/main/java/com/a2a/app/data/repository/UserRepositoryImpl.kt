package com.a2a.app.data.repository

import android.util.Log
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.network.UserApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun fetchOtp(mobile: String, token: String): Status<FetchOtpResponse> {
        return try {
            val response = userApi.fetchOtp(mobile, token, "Android")
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


    override suspend fun verifyOtp(
        mobile: String,
        otp: String,
        token: String
    ): Status<VerifyOtpModel> {
        return try {
            val response = userApi.verifyOtp(mobile, otp, token)
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

    override suspend fun registration(
        mobile: String,
        email: String,
        deviceToken: String,
        reffer: String
    ): Status<RegistrationModel> {
        return try {
            val response = userApi.registration(mobile, email, deviceToken, "Android", reffer)
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

    override suspend fun getMyOrders(
        userId: String,
        page: String,
        perPage: String
    ): Status<OrderModel> {
        return try {
            val response = userApi.getMyOrders(userId, page, perPage)
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

    override suspend fun orderUpdate(
        orderId: String,
    ): Status<OrderUpdateModel> {
        return try {
            val response = userApi.orderUpdates(orderId)
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

    override suspend fun editProfile(
        id: String,
        fullName: String,
        mobile: String
    ): Status<CommonResponseModel> {
        return try {
            val response = userApi.editProfile(id, fullName, mobile)
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

    override suspend fun addressList(userId: String): Status<AddressListModel> {
        return try {
            val response = userApi.allAddress(userId)
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

    override fun addressList1(userId: String): Flow<AddressListModel> {
        return userApi.allAddress1(userId = userId)
    }

    override suspend fun deleteAddress(
        addressId: String
    ): Status<CommonResponseModel> {
        return try {
            val response = userApi.deleteAddress(addressId)
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

    override suspend fun editAddress(
        addressId: String,
        title: String,
        address: String,
        address2: String,
        city: String,
        pinCode: String,
        contactName: String,
        contactMobile: String,
        lat: String,
        lng: String,
        state: String,
        postOffice: String
    ): Status<CommonResponseModel> {
        return try {
            val response = userApi.editAddress(
                addressId,
                title,
                address,
                address2,
                city,
                pinCode,
                contactName,
                contactMobile,
                lat,
                lng,
                state,
                postOffice
            )
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

    override suspend fun addAddress(
        userId: String,
        title: String,
        address: String,
        address2: String,
        city: String,
        pinCode: String,
        contactName: String,
        contactMobile: String,
        lat: String,
        lng: String,
        state: String,
        postOffice: String
    ): Status<AddAddressModel> {
        return try {
            val response = userApi.addAddress(
                userId,
                title,
                address,
                address2,
                city,
                pinCode,
                contactName,
                contactMobile,
                lat,
                lng,
                state,
                postOffice
            )
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


    override suspend fun getWalletData(
        userId: String,
    ): Status<WalletDataModel> {
        return try {
            val response = userApi.getWalletData(userId)
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


    override suspend fun getWalletTransaction(
        userId: String,
    ): Status<WalletTransactionModel> {
        return try {
            val response = userApi.getWalletTransactions(userId)
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

    override suspend fun assignPlan(
        userId: String,
        planId: String
    ): Status<AssignPlanModel> {
        return try {
            val response = userApi.assignPlan(userId, planId)
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

    override suspend fun confirmBooking(
        userId: String,
        pickupAddress: String,
        destinationAddress: String,
        category: String,
        subCategory: String,
        pickupRange: String,
        weight: String,
        width: String,
        height: String,
        length: String,
        pickupType: String,
        deliveryType: String,
        scheduleTime: String,
        scheduleDate: String,
        price: String,
        finalPrice: String,
        timeslot: String,
        videoRecording: String,
        pictureRecording: String,
        liveTemparature: String,
        liveTracking: String,
        sgst: String,
        cgst: String,
        igst: String
    ): Status<ConfirmBookingModel> {
        return try {
            val response = userApi.confirmBooking(
                userId,
                pickupAddress,
                destinationAddress,
                category,
                subCategory,
                pickupRange,
                weight,
                width,
                height,
                length,
                pickupType,
                deliveryType,
                scheduleTime,
                scheduleDate,
                price,
                finalPrice,
                timeslot,
                videoRecording,
                pictureRecording,
                liveTemparature,
                liveTracking,
                sgst, cgst, igst
            )
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

    override suspend fun estimateBooking(
        userId: String,
        pickupAddress: String,
        destinationAddress: String,
        category: String,
        subCategory: String,
        pickupRange: String,
        weight: String,
        width: String,
        height: String,
        length: String,
        pickupType: String,
        deliveryType: String,
        scheduleTime: String,
        scheduleDate: String,
        videoRecording: String,
        pictureRecording: String,
        liveTemparature: String,
        liveTracking: String
    ): Status<EstimateBookingModel> {
        return try {
            val response = userApi.estimateBooking(
                userId,
                pickupAddress,
                destinationAddress,
                category,
                subCategory,
                pickupRange,
                weight,
                width,
                height,
                length,
                pickupType,
                deliveryType,
                scheduleTime,
                scheduleDate,
                videoRecording,
                pictureRecording,
                liveTemparature,
                liveTracking
            )
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

    override suspend fun checkOrderStatus(orderId: String): Status<CheckOrderStatusModel> {
        return try {
            val response = userApi.checkOrderStatus(orderId)
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

    override suspend fun normalTimeSlots(
        scheduleDate: String,
        destinationAddress: String,
        pickupAddress: String
    ): Status<NormalTimeslotModel> {
        return try {
            val response = userApi.normalTimeslots(scheduleDate, destinationAddress, pickupAddress)
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
    ): Status<CheckCutOffTimeModel> {
        return try {
            val response = userApi.checkCutOffTime(startCity, endCity)
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

    override suspend fun getEstimateBooking(
        userId: String,
        pickupAddress: String,
        destinationAddress: String,
        category: String,
        subCategory: String,
        pickupRange: String,
        weight: String,
        width: String,
        height: String,
        length: String,
        pickupType: String,
        deliveryType: String,
        scheduleTime: String,
        scheduleDate: String,
        videoRecording: String,
        pictureRecording: String,
        liveTemparature: String,
        liveTracking: String
    ): EstimateBookingModel {
        return userApi.estimateBooking(
            userId,
            pickupAddress,
            destinationAddress,
            category,
            subCategory,
            pickupRange,
            weight,
            width,
            height,
            length,
            pickupType,
            deliveryType,
            scheduleTime,
            scheduleDate,
            videoRecording,
            pictureRecording,
            liveTemparature,
            liveTracking
        )
    }
}