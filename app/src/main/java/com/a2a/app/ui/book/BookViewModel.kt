package com.a2a.app.ui.book

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Resource
import com.a2a.app.data.model.CheckCutOffTimeModel
import com.a2a.app.data.model.PaidAdditionalServices
import com.a2a.app.domain.use_case.user_use_case.BookUseCases
import com.a2a.app.toDate
import com.a2a.app.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    private val appUtils: AppUtils
) : ViewModel() {

    private val _state = mutableStateOf(BookState())
    val state: State<BookState> = _state

    private var serviceTypes: List<String> = emptyList()

    init {
        getCategories()
        getServices()
    }

    fun onEvent(event: BookEvent) {
        Log.e("Book View Model :", "$event -> \n$state")
        when (event) {
            is BookEvent.GetAddresses -> {
                getAddresses(appUtils.getUser()!!.id)
            }
            is BookEvent.GetCategories -> {
                // getCategories()
                Log.e("Book View Model", "onEvent: Get Category")
            }
            is BookEvent.GetServices -> {
                getServices()
            }
            is BookEvent.CutOffTimeCheck -> {}
            is BookEvent.GetSubCategories -> {
                getSubCategories(event.categoryId)
            }
            is BookEvent.CategoryChanged -> {
                _state.value = state.value.copy(category = event.value)
            }
            is BookEvent.PickupAddressChanged -> {
                val destinationAddress = _state.value.destinationAddress
                if (destinationAddress.isEmpty())
                    _state.value = state.value.copy(pickupAddress = event.value)
                else {
                    _state.value = state.value.copy(pickupAddress = event.value)
                    cutOffTimeCheck()
                }
            }
            is BookEvent.DestinationAddressChanged -> {
                _state.value = state.value.copy(destinationAddress = event.value)
                cutOffTimeCheck()
            }
            is BookEvent.HeightChanged -> {
                _state.value = state.value.copy(height = event.value)
            }
            is BookEvent.PickupDateChanged -> {
                _state.value = state.value.copy(pickupDate = event.value)
            }
            is BookEvent.TimeslotChanged -> {
                _state.value = state.value.copy(timeSlot = event.value)
            }
            is BookEvent.LengthChanged -> {
                _state.value = state.value.copy(length = event.value)
            }
            is BookEvent.LiveGPSChanged -> {
                _state.value = state.value.copy(liveGPS = event.value)
            }
            is BookEvent.LiveTemperatureChanged -> {
                _state.value = state.value.copy(liveTemperature = event.value)
            }
            is BookEvent.PickupRangeChanged -> {
                _state.value = state.value.copy(pickupRange = event.value)
            }
            is BookEvent.PictureChanged -> {
                _state.value = state.value.copy(picture = event.value)
            }
            is BookEvent.RemarksChanged -> {
                _state.value = state.value.copy(remarks = event.value)
            }
            is BookEvent.ServiceTypeChanged -> {
                _state.value = state.value.copy(serviceType = event.value)
            }
            is BookEvent.SubCategoryChanged -> {
                _state.value = state.value.copy(subCategory = event.value)
            }
            is BookEvent.VideoRecordingChanged -> {
                _state.value = state.value.copy(videoRecording = event.value)
            }
            is BookEvent.WeightChanged -> {
                _state.value = state.value.copy(weight = event.value)
            }
            is BookEvent.WidthChanged -> {
                _state.value = state.value.copy(width = event.value)
            }
            is BookEvent.EstimateBooking -> {

            }
            else -> {}
        }
    }

    private fun getCategories() {
        bookUseCases.categoryUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value =
                            state.value.copy(isLoading = false, categories = result.data!!.result)
                        //Log.e("Book View Model ", state.toString())
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getServices() {
        bookUseCases.serviceTypeUseCase()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        serviceTypes =
                            result.data!!.result.map { it.name }
                        _state.value =
                            state.value.copy(isLoading = false)
                        //Log.e("Book View Model ", state.toString())
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getSubCategories(categoryId: String) {
        bookUseCases.subCategoryUseCase(categoryId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            subCategories = result.data!!.result
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getAddresses(userId: String) {
        bookUseCases.addressListUseCase(userId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            addresses = result.data!!.result
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun cutOffTimeCheck() {
        val startCity = _state.value.pickupAddress[0].city.id
        val endCity = _state.value.destinationAddress[0].city.id
        bookUseCases.cutoffTimeCheckUseCase(
            startCity = startCity,
            endCity = endCity
        )
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val additionalService = getPaidAdditionalServices(result.data!!.result)
                        val services = getActivatedServices(result.data.result)

                        Log.e(
                            "Book view model ",
                            "Addition services : $additionalService\nServices : $services"
                        )

                        _state.value = state.value.copy(
                            isLoading = false,
                            additionalService = additionalService,
                            serviceTypes = services
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun estimateBooking() {
        state.value.run {
            bookUseCases.estimateBookingUseCase(
                userId = appUtils.getUser()!!.id,
                pickupAddress = pickupAddress[0].id,
                destinationAddress = destinationAddress[0].id,
                category = category[0].id,
                subCategory = subCategory[0].id,
                pickupRange = pickupRange,
                weight = weight,
                width = width,
                height = height,
                length = length,
                pickupType = serviceType.replace(" ", ""),
                deliveryType = serviceType.replace(" ", ""),
                scheduleDate = if (serviceType == "normal") pickupDate.toDate(
                    "yyyy-MM-dd",
                    "dd/MM/yyyy"
                ) else "",
                scheduleTime = if (serviceType == "Normal") timeSlot else "",
                videoRecording = if (videoRecording) "Y" else "N",
                pictureRecording = if (picture) "Y" else "N",
                liveTracking = if (liveGPS) "Y" else "N",
                liveTemparature = if (liveTemperature) "Y" else "N"
            ).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    private fun getPaidAdditionalServices(data: CheckCutOffTimeModel.Result): PaidAdditionalServices {
        return data.run {
            PaidAdditionalServices(
                pictureRecordingAvailable = picture_recording == "1",
                videoRecordingAvailable = picture_recording == "1",
                liveGPSTrackingAvailable = picture_recording == "1",
                liveTemperatureTrackingAvailable = picture_recording == "1"
            )
        }
    }

    private fun getActivatedServices(data: CheckCutOffTimeModel.Result): List<String> {
        val activatedServicesInCutOffTime = ArrayList<String>()

        if (data.normal) activatedServicesInCutOffTime.add("normal")
        if (data.express) activatedServicesInCutOffTime.add("express")
        if (data.super_fast) activatedServicesInCutOffTime.add("super fast")

        val finalServiceList = ArrayList<String>()
        for (service in serviceTypes) {
            for (cutOffService in activatedServicesInCutOffTime) {
                Log.e("book", "Service type : $service\n Cut off time service : $cutOffService")
                if (service.lowercase() == cutOffService)
                    finalServiceList.add(service)
            }
        }

        //Log.e("Book view model ", finalServiceList.toString())

        return finalServiceList
    }

}