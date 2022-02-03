package com.langlab.dadjokeflow.repository

sealed class RemoteResult<out T> {
    data class Success<T>(val data: T) : RemoteResult<T>()
    data class Error(val exception: Exception?) : RemoteResult<Nothing>()
}