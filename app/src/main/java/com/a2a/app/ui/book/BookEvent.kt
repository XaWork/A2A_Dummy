package com.a2a.app.ui.book

sealed class BookEvent{
    object GetCategories: BookEvent()
    object GetServices: BookEvent()
    data class GetAddresses(val userId: String): BookEvent()
    data class CutOffTimeCheck(val pickupCit: String, val destinationCity: String): BookEvent()
    data class GetSubCategories(val categoryId: String):BookEvent()
}
