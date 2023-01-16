package com.a2a.app.domain.repository

import com.a2a.app.data.model.AddressListModel

interface ProfileRepository {
    suspend fun addressList(userId: String): AddressListModel
}