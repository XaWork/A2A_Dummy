package com.a2a.app.common

sealed class Status<out T>{
    data class Success<out T>(val value: T): Status<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ): Status<Nothing>()
    object Loading: Status<Nothing>()
    //object Idle: Status<Nothing>()
}
