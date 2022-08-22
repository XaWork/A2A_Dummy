package com.a2a.app.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.data.viewmodel.UserViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
           //modelClass.isAssignableFrom(CategoryViewModel::class.java) -> CategoryViewModel(repository as CategoryRepository) as T
           modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
           //modelClass.isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(repository as ProductRepository) as T
           modelClass.isAssignableFrom(CustomViewModel::class.java) -> CustomViewModel(repository as CustomRepository) as T
            else -> throw IllegalArgumentException("ViewModel class not found")
        }
    }
}