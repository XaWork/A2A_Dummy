package com.a2a.app.data.repository

import com.a2a.app.common.Status
import com.a2a.app.data.model.*

interface UserRepository1 {

    suspend fun fetchOtp(mobile: String, token: String): Status<FetchOtpResponse>

    suspend fun verifyOtp(
        mobile: String,
        otp: String,
        token: String
    ): Status<VerifyOtpModel>

    suspend fun registration(
        mobile: String,
        email: String,
        deviceToken: String,
        reffer: String,
    ): Status<RegistrationModel>

    suspend fun getMyOrders(
        userId: String,
        page: String,
        perPage: String
    ): Status<OrderModel>

    suspend fun orderUpdate(
        orderId: String
    ): Status<OrderUpdateModel>

    suspend fun addressList(
        userId: String
    ):Status<AddressListModel>

    suspend fun deleteAddress(
        userId: String,
        addressId: String
    ):Status<CommonResponseModel>

    suspend fun editAddress(
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
        postOffice: String,
    ): Status<CommonResponseModel>

    suspend fun addAddress(
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
        postOffice: String,
    ): Status<AddAddressModel>

    suspend fun getWalletData(userId: String): Status<WalletDataModel>

    suspend fun getWalletTransaction(userId: String): Status<WalletTransactionModel>

    suspend fun assignPlan(userId: String, planId: String): Status<AssignPlanModel>
}