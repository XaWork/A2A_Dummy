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
}