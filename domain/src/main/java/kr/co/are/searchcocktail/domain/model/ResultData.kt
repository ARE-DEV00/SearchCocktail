package kr.co.are.searchcocktail.domain.model

sealed class ResultData<out T> {
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Error(val exception: Throwable) : ResultData<Nothing>()
    data object Loading : ResultData<Nothing>()
}