package com.a2a.app.domain.use_case.custom_use_case

import com.a2a.app.common.Resource
import com.a2a.app.data.model.AllCategoryModel
import com.a2a.app.data.repository.CustomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val customRepository: CustomRepository
) {
    operator fun invoke(): Flow<Resource<AllCategoryModel>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = customRepository.getAllCategories()

            if (response.status == "success") {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error(message = response.message))
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