package com.a2a.app.data.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.viewmodel.CustomViewModel

class CustomViewModelFactory(private val repository: CustomRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomViewModel(repository) as T
    }
}