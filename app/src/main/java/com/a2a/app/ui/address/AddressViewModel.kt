package com.a2a.app.ui.address

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddAddressModel
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.CommonResponseModel
import com.a2a.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _allAddresses = MutableLiveData<Status<AddressListModel>>()
    val allAddresses: LiveData<Status<AddressListModel>> = _allAddresses

    fun getAllAddresses(userId: String) {
        _allAddresses.value = Status.Loading
        viewModelScope.launch {
            _allAddresses.value = userRepository.addressList(userId)
        }
    }

    fun deleteAddress(userId: String, addressId: String) {
        _allAddresses.value = Status.Loading
        viewModelScope.launch {
            userRepository.deleteAddress(addressId = addressId)
            _allAddresses.value = userRepository.addressList(userId)
        }
    }


    var fullName by mutableStateOf("")
    var phone by mutableStateOf("")
    var address1 by mutableStateOf("")
    var address2 by mutableStateOf("")
    var country by mutableStateOf("India")
    var state by mutableStateOf("")
    var city by mutableStateOf("")
    var pincode by mutableStateOf("")
    var landmark by mutableStateOf("")
    var addressType by mutableStateOf("")
    var lat by mutableStateOf("")
    var lang by mutableStateOf("")
    var addressTypes = listOf("Home", "Work", "Other").toMutableStateList()
    var countries = listOf("India").toMutableStateList()

    fun updateFullName(update: String) {
        fullName = update
    }

    fun updatePhone(update: String) {
        phone = update
    }

    fun updateAddress1(update: String) {
        address1 = update
    }

    fun updateAddress2(update: String) {
        address2 = update
    }

    fun updateCountry(update: String) {
        country = update
    }

    fun updateState(update: String) {
        state = update
    }

    fun updateCity(update: String) {
        city = update
    }

    fun updatePincode(update: String) {
        pincode = update
    }

    fun updateLandmark(update: String) {
        landmark = update
    }

    fun updateAddressType(update: String) {
        addressType = update
    }

    fun updateLatLang(updateLat: String, updateLang: String) {
        lat = updateLat
        lang = updateLang
    }

    fun updateTextFields(address: AddressListModel.Result?) {
        if (address != null) {
            fullName = address.contactName
            phone = address.contactMobile
            address1 = address.address
            address2 = address.address2
            state = address.state.name
            city = address.city.name
            pincode = address.pincode
            addressType = address.title
        } else {
            fullName = ""
            phone = ""
            address1 = ""
            address2 = ""
            state = ""
            city = ""
            pincode = ""
            addressType = ""
        }
    }

    private var _saveAddressResponse = MutableLiveData<Status<AddAddressModel>>()
    val saveAddressResponse: LiveData<Status<AddAddressModel>> = _saveAddressResponse

    fun addAddress(userId: String, cityId: String, stateId: String) {
        _saveAddressResponse.value = Status.Loading
        viewModelScope.launch {
            _saveAddressResponse.value = userRepository.addAddress(
                userId = userId,
                title = addressType,
                address = address1,
                address2 = address2,
                city = cityId,
                state = stateId,
                pinCode = pincode,
                contactName = fullName,
                contactMobile = phone,
                lat = lat,
                lng = lang,
                postOffice = landmark
            )
        }
    }

    private var _editAddressResponse = MutableLiveData<Status<CommonResponseModel>>()
    val editAddressResponse: LiveData<Status<CommonResponseModel>> = _editAddressResponse
    fun editAddress(addressId: String, cityId: String, stateId: String) {
        _editAddressResponse.value = Status.Loading
        viewModelScope.launch {
            _editAddressResponse.value = userRepository.editAddress(
                addressId = addressId,
                title = addressType,
                address = address1,
                address2 = address2,
                city = cityId,
                state = stateId,
                pinCode = pincode,
                contactName = fullName,
                contactMobile = phone,
                lat = lat,
                lng = lang,
                postOffice = landmark
            )
        }
    }

}