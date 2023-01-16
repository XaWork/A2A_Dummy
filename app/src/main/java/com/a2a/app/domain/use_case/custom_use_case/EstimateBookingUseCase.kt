package com.a2a.app.domain.use_case.custom_use_case

import com.a2a.app.common.Resource
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.model.EstimateBookingModel
import com.a2a.app.data.repository.CustomRepository
import com.a2a.app.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EstimateBookingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        userId: String,
        pickupAddress: String,
        destinationAddress: String,
        category: String,
        subCategory: String,
        pickupRange: String,
        weight: String,
        width: String,
        height: String,
        length: String,
        pickupType: String,
        deliveryType: String,
        scheduleTime: String,
        scheduleDate: String,
        videoRecording: String,
        pictureRecording: String,
        liveTemparature: String,
        liveTracking: String,
    ): Flow<Resource<EstimateBookingModel>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = userRepository.getEstimateBooking(
                userId,
                pickupAddress,
                destinationAddress,
                category,
                subCategory,
                pickupRange,
                weight,
                width,
                height,
                length,
                pickupType,
                deliveryType,
                scheduleTime,
                scheduleDate,
                videoRecording,
                pictureRecording,
                liveTemparature,
                liveTracking
            )

            if (response.status == "success") {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(message = "Try again"))
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