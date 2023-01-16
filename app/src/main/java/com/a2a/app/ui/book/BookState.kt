package com.a2a.app.ui.book

import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.AllSubCategoryModel

data class BookState(
    val categories: List<AllCategoryModel.Result> = emptyList(),
    val subCategories: List<AllSubCategoryModel.Result> = emptyList(),
    val addresses: List<AddressListModel.Result> = emptyList(),
    val serviceTypes: List<String> = emptyList(),
    val additionalService: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
