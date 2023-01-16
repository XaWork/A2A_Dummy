package com.a2a.app.data.repository

import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.network.UserApi
import com.a2a.app.domain.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val api: UserApi
): ProfileRepository {

    override suspend fun addressList(userId: String): AddressListModel {
        return api.allAddress(userId)
    }
}