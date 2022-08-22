package com.a2a.app.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel (private val userRepository: UserRepository) : ViewModel() {

    //private val viewUtils: ViewUtils? = null
    //var mobile: String? = null
    //var email: String? = null

    private var _fetchOtpResponse = MutableLiveData<Status<FetchOtpResponse>>()
    val fetchOtpResponse = _fetchOtpResponse
    fun fetchOtp(mobile: String, token: String) {
        viewModelScope.launch {
            _fetchOtpResponse.value = Status.Loading
            _fetchOtpResponse.value = userRepository.fetchOtp(mobile = mobile, token)
        }
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
            result.value = userRepository.registration(mobile, email, deviceToken, reffer)
        }

        return result
    }

    fun verifyOtp(
        mobile: String,
        otp: String,
        deviceToken: String
    ): MutableLiveData<Status<VerifyOtpModel>> {

        val result = MutableLiveData<Status<VerifyOtpModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository.verifyOtp(mobile, otp, deviceToken)
        }
        return result
    }

    fun editProfile(
        id: String,
        fullName: String,
        mobile: String
    ): MutableLiveData<Status<CommonResponseModel>> {

        val result = MutableLiveData<Status<CommonResponseModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository.editProfile(id, fullName, mobile)
        }

        return result
    }

    private val _addressList = MutableLiveData<Status<AddressListModel>>()
    val addressList = _addressList
    fun allAddress(
        userId: String
    ){
        viewModelScope.launch {
            _addressList.value = Status.Loading
            _addressList.value = userRepository.allAddress(userId)
        }
    }

    private val _walletData = MutableLiveData<Status<WalletDataModel>>()
    val walletData = _walletData
    fun getWalletData(
        userId: String
    ){
        viewModelScope.launch {
            _walletData.value = Status.Loading
            _walletData.value = userRepository.getWalletData(userId)
        }
    }

    private val _walletTransaction = MutableLiveData<Status<WalletTransactionModel>>()
    val walletTransaction = _walletTransaction
    fun getWalletTransactions(
        userId: String
    ){
        viewModelScope.launch {
            _walletTransaction.value = Status.Loading
            _walletTransaction.value = userRepository.getWalletTransactions(userId)
        }
    }

    fun deleteAddress(
        userId: String,
        addressId: String
    ): MutableLiveData<Status<CommonResponseModel>> {

        val result = MutableLiveData<Status<CommonResponseModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = userRepository.deleteAddress(userId, addressId)
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
            result.value = userRepository.addAddress(
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
            result.value = userRepository.editAddress(
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

    private val _myOrders = MutableLiveData<Status<OrderModel>>()
    val myOrders = _myOrders
    fun getMyOrders(
        userId: String,
        page: String,
        perPage: String
    ) {
        viewModelScope.launch {
            _myOrders.value = Status.Loading
            _myOrders.value = userRepository.getMyOrders(userId, page, perPage)
        }
    }
}