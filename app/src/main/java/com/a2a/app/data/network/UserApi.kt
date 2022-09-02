package com.a2a.app.data.network

import com.a2a.app.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun fetchOtp(
        @Field("mobile") mobile: String,
        @Field("device_token") token: String,
        @Field("device_type") device: String
    ): FetchOtpResponse

    @FormUrlEncoded
    @POST("registration")
    suspend fun registration(
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("device_token") deviceToken: String,
        @Field("device_type") deviceType: String = "Android",
        @Field("reffer") reffer: String = "",
    ): RegistrationModel

    @FormUrlEncoded
    @POST("verify-otp")
    suspend fun verifyOtp(
        @Field("mobile") phone: String,
        @Field("otp") otp: String,
        @Field("device_token") deviceToken: String
    ): VerifyOtpModel

    @FormUrlEncoded
    @POST("edit-profile")
    suspend fun editProfile(
        @Field("id") id: String,
        @Field("full_name") fullName: String,
        @Field("mobile") mobile: String
    ): CommonResponseModel

    @GET("all-address")
    suspend fun allAddress(
        @Query("id") userId: String
    ): AddressListModel

    @GET("delete-address")
    suspend fun deleteAddress(
        @Query("id") userId: String,
        @Query("addressId") addressId: String
    ): CommonResponseModel

    @FormUrlEncoded
    @POST("add-address")
    suspend fun addAddress(
        @Field("userid") userId: String,
        @Field("title") title: String,
        @Field("address") address: String,
        @Field("address2") address2: String,
        @Field("city") city: String,
        @Field("pincode") pinCode: String,
        @Field("contact_name") contactName: String,
        @Field("contact_mobile") contactMobile: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("state") state: String,
        @Field("post_office") postOffice: String,
    ): AddAddressModel

    @FormUrlEncoded
    @POST("edit-address")
    suspend fun editAddress(
        @Field("id") userId: String,
        @Field("title") title: String,
        @Field("address") address: String,
        @Field("address2") address2: String,
        @Field("city") city: String,
        @Field("pincode") pinCode: String,
        @Field("contact_name") contactName: String,
        @Field("contact_mobile") contactMobile: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String,
        @Field("state") state: String,
        @Field("post_office") postOffice: String,
    ): CommonResponseModel

    @GET("my-orders")
    suspend fun getMyOrders(
        @Query("id") userId: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String
    ): OrderModel

    @GET("get-wallet-data")
    suspend fun getWalletData(
        @Query("id") userId: String
    ): WalletDataModel

    @POST("wallet-transactions")
    suspend fun getWalletTransactions(
        @Query("id") userId: String
    ): WalletTransactionModel

    @FormUrlEncoded
    @POST("confirm-booking")
    suspend fun confirmInstantBooking(
        @Field("id") userId: String,
        @Field("pickup_address") pickupAddress: String,
        @Field("destination_address") destinationAddress: String,
        @Field("category") category: String,
        @Field("sub_category") subCategory: String,
        @Field("pickup_range") pickupRange: String,
        @Field("weight") weight: String = "",
        @Field("width") width: String,
        @Field("height") height: String,
        @Field("length") length: String,
        @Field("pickup_type") pickupType: String,
        @Field("delivery_type") deliveryType: String,
    ): ScheduleBookingModel

    @FormUrlEncoded
    @POST("estimate-booking")
    suspend fun estimateBooking(
        @Field("id") userId: String,
        @Field("pickup_address") pickupAddress: String,
        @Field("destination_address") destinationAddress: String,
        @Field("category") category: String,
        @Field("sub_category") subCategory: String,
        @Field("pickup_range") pickupRange: String,
        @Field("weight") weight: String = "",
        @Field("width") width: String,
        @Field("height") height: String,
        @Field("length") length: String,
        @Field("pickup_type") pickupType: String,
        @Field("delivery_type") deliveryType: String,
    ): ScheduleBookingModel
}