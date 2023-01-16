package com.a2a.app.ui.book

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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
class DummyViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
) : ViewModel() {

    private val _state = mutableStateOf(BookState())
    val state: State<BookState> = _state

    val state1 by mutableStateOf(BookState())


    fun dummy(){
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
}