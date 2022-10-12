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
class CustomViewModel @Inject constructor(
    private val repository: CustomRepository
) : ViewModel() {

    fun getAllStates(): MutableLiveData<Status<StateListModel>> {
        val result = MutableLiveData<Status<StateListModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.allStates()
        }
        return result
    }

    fun getZipByCity(cityId: String): MutableLiveData<Status<ZipListModel>> {
        val result = MutableLiveData<Status<ZipListModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.zipByCity(cityId)
        }
        return result
    }


    fun getAllCityByState(stateId: String): MutableLiveData<Status<CityListModel>> {
        val result = MutableLiveData<Status<CityListModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.cityByState(stateId)
        }
        return result
    }


    fun getAllCategories(): MutableLiveData<Status<AllCategoryModel>> {
        val result = MutableLiveData<Status<AllCategoryModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.allCategories()
        }
        return result
    }


    fun getAllSubCategories(categoryId: String): MutableLiveData<Status<AllSubCategoryModel>> {
        val result = MutableLiveData<Status<AllSubCategoryModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.allSubCategories(categoryId)
        }
        return result
    }

    fun getAllCities(): MutableLiveData<Status<CityModel>> {
        val result = MutableLiveData<Status<CityModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.allCities()
        }
        return result
    }

    fun offerDeal(): MutableLiveData<Status<OfferDealModel>> {
        val result = MutableLiveData<Status<OfferDealModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.offerDeal()
        }
        return result
    }

    fun getHome(): MutableLiveData<Status<HomeModel>> {
        val result = MutableLiveData<Status<HomeModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.home()
        }
        return result
    }

    fun allPlans(): MutableLiveData<Status<AllPlanModel>> {
        val result = MutableLiveData<Status<AllPlanModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.allPlans()
        }
        return result
    }

    fun serviceTypes(): MutableLiveData<Status<ServiceTypeModel>> {
        val result = MutableLiveData<Status<ServiceTypeModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.serviceTypes()
        }
        return result
    }
}