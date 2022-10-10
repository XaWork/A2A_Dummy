package com.a2a.app.ui.book

import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.AllSubCategoryModel
import com.a2a.app.data.model.EstimateBookingModel

data class OrderConfirmationData(
    val category: AllCategoryModel.Result,
    val subCategory: AllSubCategoryModel.Result,
    val weight: String,
    val estimateBookingResponse: EstimateBookingModel,
    val deliveryType: String,
    val pickupDate: String,
    val pickupTime: String,
    val deliveryTime: String,
    val deliveryDate: String,
    val videoRecording: String,
    val pictureRecording: String,
    val liveTemperature: String,
    val liveTracking: String,
    val pickupRange: String,
    val width: String,
    val length: String,
    val height: String,
    val pickupLocation: AddressListModel.Result,
    val deliveryLocation: AddressListModel.Result,
)
