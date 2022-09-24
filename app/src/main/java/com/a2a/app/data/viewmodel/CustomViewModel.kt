package com.a2a.app.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Status
import com.a2a.app.data.model.*
import com.a2a.app.data.repository.CustomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomViewModel @Inject constructor(private val customRepository: CustomRepository) :
    ViewModel() {

    fun getHome(): MutableLiveData<Status<HomeModel>> {
        val result = MutableLiveData<Status<HomeModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = customRepository.home()
        }
        return result
    }

    fun allPlans(): MutableLiveData<Status<AllPlanModel>> {
        val result = MutableLiveData<Status<AllPlanModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = customRepository.allPlans()
        }
        return result
    }

    fun serviceTypes(): MutableLiveData<Status<ServiceTypeModel>> {
        val result = MutableLiveData<Status<ServiceTypeModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = customRepository.serviceTypes()
        }
        return result
    }

    private val _allCities = MutableLiveData<Status<CityModel>>()
    val allCities = _allCities
    fun getAllCities() {
        viewModelScope.launch {
            _allCities.value = Status.Loading
            _allCities.value = customRepository.allCities()
        }

    }

    private val _allCategories = MutableLiveData<Status<AllCategoryModel>>()
    val allCategories = _allCategories
    fun getAllCategories() {
        viewModelScope.launch {
            _allCategories.value = Status.Loading
            _allCategories.value = customRepository.allCategories()
        }
    }

    private val _allSubCategories = MutableLiveData<Status<AllSubCategoryModel>>()
    val allSubCategories = _allSubCategories
    fun getAllSubCategories(
        categoryId: String
    ) {
        viewModelScope.launch {
            _allSubCategories.value = Status.Loading
            _allSubCategories.value = customRepository.allSubCategories(categoryId)
        }
    }

    private val _allStates = MutableLiveData<Status<StateListModel>>()
    val allStates = _allStates
    fun getAllStates() {
        viewModelScope.launch {
            _allStates.value = Status.Loading
            _allStates.value = customRepository.allStates()
        }

    }

    private val _cityByState = MutableLiveData<Status<CityListModel>>()
    val cityByState = _cityByState
    fun getAllCityByState(
        state: String
    ) {
        viewModelScope.launch {
            _cityByState.value = Status.Loading
            _cityByState.value = customRepository.cityByState(state)
        }

    }

    private val _zipByCity = MutableLiveData<Status<ZipListModel>>()
    val zipByCity = _zipByCity
    fun getZipByCity(
        city: String
    ) {
        viewModelScope.launch {
            _zipByCity.value = Status.Loading
            _zipByCity.value = customRepository.zipByCity(city)
        }
    }

    fun checkCutOffTime(
        startCity: String,
        endCity: String,
    ): MutableLiveData<Status<CheckCutOffTimeModel>> {
        val result = MutableLiveData<Status<CheckCutOffTimeModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = customRepository.checkCutOffTime(startCity, endCity)
        }

        return result
    }

    private val _offerDeal = MutableLiveData<Status<ZipListModel>>()
    val offerDeal = _offerDeal
    fun offerDeal() {
        viewModelScope.launch {
            _offerDeal.value = Status.Loading
            _offerDeal.value = customRepository.offerDeal()
        }
    }

    /*fun createBulkOrder(bulkOrder: CustomBulkOrder): MutableLiveData<Status<>> {
        val result = MutableLiveData<Status<>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = customRepository.offerDeal()
        }
        return result

    }*/
}