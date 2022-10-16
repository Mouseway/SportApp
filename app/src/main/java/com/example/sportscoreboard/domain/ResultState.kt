package com.example.sportscoreboard.domain

enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ResultState <out T> (val status: ApiStatus, val data: T?, val message:String?) {

    data class Success<out R>(val _data: R?): ResultState<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: String, val showError: Boolean): ResultState<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )

    data class Loading<out R>(val isLoading: Boolean): ResultState<R>(
        status = ApiStatus.LOADING,
        data = null,
        message = null
    )
}