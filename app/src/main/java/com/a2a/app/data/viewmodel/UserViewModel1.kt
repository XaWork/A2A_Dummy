package com.a2a.app.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.repository.UserRepository1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel1 @Inject constructor(
    private val userRepository1: UserRepository1
) : ViewModel() {

    fun fetchOtp(mobile: String, token: String): MutableLiveData<Status<FetchOtpResponse>> {
        val result = MutableLiveData<Status<FetchOtpResponse>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.fetchOtp(mobile, token)
        }

        return result
    }


    fun verifyOtp(
        mobile: String,
        otp: String,
        token: String
    ): MutableLiveData<Status<VerifyOtpModel>> {
        val result = MutableLiveData<Status<VerifyOtpModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.verifyOtp(mobile, otp, token)
        }

        return result
    }


    fun registration(
        mobile: String,
        email: String,
        deviceToken: String,
        reffer: String
    ): MutableLiveData<Status<RegistrationModel>> {
        val result = MutableLiveData<Status<RegistrationModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.registration(mobile, email, deviceToken, reffer)
        }

        return result
    }


    fun getMyOrders(
        userId: String,
        page: String,
        perPage: String
    ): MutableLiveData<Status<OrderModel>> {
        val result = MutableLiveData<Status<OrderModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.getMyOrders(userId, page, perPage)
        }

        return result
    }

    fun orderUpdate(
        orderId: String
    ): MutableLiveData<Status<OrderUpdateModel>> {
        val result = MutableLiveData<Status<OrderUpdateModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.orderUpdate(orderId)
        }

        return result
    }


    fun addressList(
        userId: String
    ): MutableLiveData<Status<AddressListModel>> {
        val result = MutableLiveData<Status<AddressListModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.addressList(userId)
        }

        return result
    }

    fun deleteAddress(
        userId: String,
        addressId: String
    ): MutableLiveData<Status<CommonResponseModel>> {
        val result = MutableLiveData<Status<CommonResponseModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.deleteAddress(userId, addressId)
        }

        return result
    }


    fun addAddress(
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
    ): MutableLiveData<Status<AddAddressModel>> {
        val result = MutableLiveData<Status<AddAddressModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.addAddress(
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

        return result
    }

    fun editAddress(
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
    ): MutableLiveData<Status<CommonResponseModel>> {
        val result = MutableLiveData<Status<CommonResponseModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.editAddress(
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

        return result
    }


    fun getWalletData(
        userId: String
    ): MutableLiveData<Status<WalletDataModel>> {
        val result = MutableLiveData<Status<WalletDataModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.getWalletData(userId)
        }

        return result
    }


    fun getWalletTransaction(
        userId: String
    ): MutableLiveData<Status<WalletTransactionModel>> {
        val result = MutableLiveData<Status<WalletTransactionModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.getWalletTransaction(userId)
        }

        return result
    }

    fun assignPlan(
        userId: String,
        planId: String
    ): MutableLiveData<Status<AssignPlanModel>> {
        val result = MutableLiveData<Status<AssignPlanModel>>()

        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository1.assignPlan(userId, planId)
        }

        return result
    }
}