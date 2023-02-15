package com.a2a.app.ui.book

import com.a2a.app.data.model.*

data class BookState(
    val pickupAddress: List<AddressListModel.Result> = emptyList(),
    val destinationAddress: List<AddressListModel.Result> = emptyList(),
    val serviceType: String = "",
    val category: List<AllCategoryModel.Result> = emptyList(),
    val subCategory: List<AllSubCategoryModel.Result> = emptyList(),
    val pickupRange: String = "2",
    val pickupRanges: List<Int> = listOf(2, 5, 10, 15, 20, 25, 30, 35),
    val weight: String = "",
    val width: String = "",
    val height: String = "",
    val length: String = "",
    val remarks: String = "",
    val pickupDate: String = "Date",
    val timeSlot: String = "Time",
    val videoRecording: Boolean = false,
    val picture: Boolean = false,
    val liveGPS: Boolean = false,
    val liveTemperature: Boolean = false,
    val categories: List<AllCategoryModel.Result> = emptyList(),
    val subCategories: List<AllSubCategoryModel.Result> = emptyList(),
    val addresses: List<AddressListModel.Result> = emptyList(),
    val serviceTypes: List<String> = emptyList(),
    val pickupTimeSlots: List<String> = emptyList(),
    val additionalService: PaidAdditionalServices = PaidAdditionalServices(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val estimateBookingModel: EstimateBookingModel? = null

)
