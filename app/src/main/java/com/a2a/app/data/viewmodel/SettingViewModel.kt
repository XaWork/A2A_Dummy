package com.a2a.app.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a2a.app.common.Status
import com.a2a.app.data.model.SettingsModel
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: SettingRepository
) : ViewModel() {

    fun getSettings(): MutableLiveData<Status<SettingsModel>> {
        val result = MutableLiveData<Status<SettingsModel>>()
        viewModelScope.launch {
            result.value = Status.Loading
            result.value = repository.getSettings()
        }

        return result
    }

}