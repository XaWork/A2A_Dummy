package com.a2a.app.ui.book

import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.AllSubCategoryModel

sealed class BookEvent {
    data class PickupAddressChanged(val value: List<AddressListModel.Result>) : BookEvent()
    data class DestinationAddressChanged(val value: List<AddressListModel.Result>) : BookEvent()
    data class ServiceTypeChanged(val value: String) : BookEvent()
    data class CategoryChanged(val value: List<AllCategoryModel.Result>) : BookEvent()
    data class SubCategoryChanged(val value: List<AllSubCategoryModel.Result>) : BookEvent()
    data class PickupRangeChanged(val value: String) : BookEvent()
    data class WeightChanged(val value: String) : BookEvent()
    data class WidthChanged(val value: String) : BookEvent()
    data class HeightChanged(val value: String) : BookEvent()
    data class PickupDateChanged(val value: String) : BookEvent()
    data class TimeslotChanged(val value: String) : BookEvent()
    data class LengthChanged(val value: String) : BookEvent()
    data class RemarksChanged(val value: String) : BookEvent()
    data class VideoRecordingChanged(val value: Boolean) : BookEvent()
    data class PictureChanged(val value: Boolean) : BookEvent()
    data class LiveGPSChanged(val value: Boolean) : BookEvent()
    data class LiveTemperatureChanged(val value: Boolean) : BookEvent()
    object GetCategories : BookEvent()
    object GetServices : BookEvent()
    object GetAddresses : BookEvent()
    object EstimateBooking: BookEvent()
    data class CutOffTimeCheck(val pickupCit: String, val destinationCity: String) : BookEvent()
    data class GetSubCategories(val categoryId: String) : BookEvent()
}
