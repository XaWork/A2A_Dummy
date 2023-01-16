package com.a2a.app.ui.book

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Resource
import com.a2a.app.domain.use_case.user_use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(BookState())
    val state: State<BookState> = _state

    init {
        getCategories()
        getServices()
    }

    fun onEvent(event: BookEvent) {
        when (event) {
            is BookEvent.GetAddresses -> {}
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
        }
    }

    private fun getAddresses() {
        /* userCases.addressList(userId = appUtils.getUser()!!.id)
             .onEach { addresses ->
                 _state.value =state.value.copy(addresses = addresses.data!!.result)
             }*/
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
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
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
                        val serviceTypes =
                            result.data!!.result.map { it.name }
                        _state.value =
                            state.value.copy(isLoading = false, serviceTypes = serviceTypes)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false, error = result.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getSubCategories(categoryId: String) {
        bookUseCases.subCategoryUseCase(categoryId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                      //  _state.value = state.value.copy(isLoading = true)
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
                }
            }.launchIn(viewModelScope)
    }

}