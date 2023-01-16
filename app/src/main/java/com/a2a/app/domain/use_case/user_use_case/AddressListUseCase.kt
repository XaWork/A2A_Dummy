package com.a2a.app.domain.use_case.user_use_case

import com.a2a.app.common.Resource
import com.a2a.app.common.Status
import com.a2a.app.data.model.AddressListModel
import com.a2a.app.data.repository.UserRepository
import com.a2a.app.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddressListUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    operator fun invoke(userId: String): Flow<Resource<AddressListModel>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = repository.addressList(userId)
            if (response.status == "success")
                emit(Resource.Success(response))
            else
                emit(Resource.Error(message = response.message))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(message = "Couldn't load data"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(message = "Couldn't load data"))
        }

    }
}