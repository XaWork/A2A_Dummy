package com.a2a.app.data.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel

class UserViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}