package com.a2a.app.domain.use_case.custom_use_case

import com.a2a.app.common.Resource
import com.a2a.app.data.model.NormalTimeslotModel
import com.a2a.app.data.repository.CustomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CheckAvailableTimeSlotsUseCase @Inject constructor(
    private val customRepository: CustomRepository
) {
    operator fun invoke(
        scheduleDate: String,
        destinationAddress: String,
        pickupAddress: String
    ): Flow<Resource<NormalTimeslotModel>> = flow {
        emit(Resource.Loading(true))
        try {
            val response =
                customRepository.normalTimeSlots(scheduleDate, destinationAddress, pickupAddress)

            if (response.status == "success") {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(message = "Getting error try again!"))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't load data"))
            e.printStackTrace()
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Couldn't load data"))
            e.printStackTrace()
        }
    }
}