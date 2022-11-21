package com.a2a.app.ui.bulkinquiry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Status
import com.a2a.app.data.model.CityModel
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BulkInquiryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val customRepository: CustomRepository,
) : ViewModel() {

    var fullName by mutableStateOf("")
    var email by mutableStateOf("")
    var mobile by mutableStateOf("")
    var address by mutableStateOf("")
    var city by mutableStateOf("")
    var cityId  = ""
    var message by mutableStateOf("")

    fun updateFullName(update: String) {
        fullName = update
    }

    fun updateEmail(update: String) {
        email = update
    }

    fun updateMobile(update: String) {
        mobile = update
    }

    fun updateAddress(update: String) {
        address = update
    }

    fun updateCity(update: String) {
        city = update
    }

    fun updateCityId(update: String) {
        cityId = update
    }

    fun updateMessage(update: String) {
        message = update
    }

    private var _cities = MutableLiveData<Status<CityModel>>()
    val cities : LiveData<Status<CityModel>> = _cities
    fun getAllCities(){
        _cities.value = Status.Loading
        viewModelScope.launch {
            _cities.value = customRepository.allCities()
        }
    }
}