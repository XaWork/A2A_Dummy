package com.a2a.app.data.repository

import com.a2a.app.common.BaseRepository
import com.a2a.app.data.network.UserApi

class UserRepository(private val userAPI: UserApi) : BaseRepository() {

    suspend fun fetchOtp(
        mobile: String,
        token: String,
    ) = safeApiCall { userAPI.fetchOtp(mobile, token, "Android") }

    suspend fun registration(
        mobile: String,
        email: String,
        deviceToken: String,
        reffer: String
    ) = safeApiCall { userAPI.registration(mobile, email, deviceToken, "Android", reffer) }

    suspend fun verifyOtp(
        mobile: String,
        otp: String,
        deviceToken: String
    ) = safeApiCall { userAPI.verifyOtp(mobile, otp, deviceToken) }

    suspend fun allAddress(
        userId: String
    ) = safeApiCall { userAPI.allAddress(userId) }

    suspend fun assignPlan(
        userId: String,
        planId: String
    ) = safeApiCall { userAPI.assignPlan(userId, planId) }

    suspend fun normalTimeSlots(
        scheduleDate: String,
        destinationAddress: String,
        pickupAddress: String
    ) = safeApiCall { userAPI.normalTimeslots(scheduleDate, destinationAddress, pickupAddress) }

    suspend fun deleteAddress(
        userId: String,
        addressId: String
    ) = safeApiCall { userAPI.deleteAddress(userId, addressId) }

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
    ) = safeApiCall {
        userAPI.addAddress(
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
    }

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
    ) = safeApiCall {
        userAPI.editAddress(
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
    }

    suspend fun editProfile(
        id: String,
        fullName: String,
        mobile: String
    ) = safeApiCall { userAPI.editProfile(id, fullName, mobile) }

    suspend fun getMyOrders(
        userId: String,
        page: String,
        perPage: String
    ) = safeApiCall { userAPI.getMyOrders(userId, page, perPage) }

    suspend fun getWalletData(
        userId: String
    ) = safeApiCall { userAPI.getWalletData(userId) }

    suspend fun getWalletTransactions(
        userId: String
    ) = safeApiCall { userAPI.getWalletTransactions(userId) }

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
    ) = safeApiCall {
        userAPI.confirmBooking(
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
            liveTracking
        )
    }

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
    ) = safeApiCall {
        userAPI.estimateBooking(
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