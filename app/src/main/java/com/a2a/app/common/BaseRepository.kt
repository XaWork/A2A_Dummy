package com.a2a.app.common

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Singleton

abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Status<T> {
        return withContext(Dispatchers.IO) {
            try {
                Status.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.e("exception", "safeapicall: $throwable")
                when (throwable) {
                    is HttpException -> {
                        Status.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody().toString()
                        )
                    }
                    else -> {
                        Status.Failure(true, null, throwable.message.toString())
                    }
                }
            }
        }
    }
}