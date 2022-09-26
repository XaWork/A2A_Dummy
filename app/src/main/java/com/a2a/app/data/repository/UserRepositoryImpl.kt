package com.a2a.app.data.repository

import android.util.Log
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.network.UserApi
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository1 {

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
        orderid: String,
    ): Status<OrderUpdateModel> {
        return try {
            val response = userApi.orderUpdates(orderid)
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

    override suspend fun deleteAddress(
        userId: String,
        addressId: String
    ): Status<CommonResponseModel> {
        return try {
            val response = userApi.deleteAddress(userId, addressId)
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
    ): Status<CommonResponseModel> {
        return try {
            val response = userApi.editAddress(
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
}