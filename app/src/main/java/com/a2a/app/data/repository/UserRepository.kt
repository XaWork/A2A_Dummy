package com.a2a.app.data.repository

import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface UserRepository {

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

    suspend fun editProfile(
        id: String,
        fullName: String,
        mobile: String
    ): Status<CommonResponseModel>

    suspend fun addressList(
        userId: String
    ): Status<AddressListModel>

    fun addressList1(
        userId: String
    ): Flow<AddressListModel>

    suspend fun deleteAddress(
        addressId: String
    ): Status<CommonResponseModel>

    suspend fun editAddress(
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

    suspend fun confirmBooking(
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
    ): Status<ConfirmBookingModel>

    suspend fun estimateBooking(
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
        liveTracking: String,
    ): Status<EstimateBookingModel>

    suspend fun getEstimateBooking(
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
        liveTracking: String,
    ):EstimateBookingModel

    suspend fun checkOrderStatus(orderId: String): Status<CheckOrderStatusModel>

    suspend fun normalTimeSlots(
        scheduleDate: String,
        destinationAddress: String,
        pickupAddress: String
    ): Status<NormalTimeslotModel>

    suspend fun checkCutOffTime(
        startCity: String,
        endCity: String,
    ): Status<CheckCutOffTimeModel>
}