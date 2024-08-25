package kr.co.are.searchcocktail.domain.model

sealed class ResultDomain<out T> {
    data class Success<out T>(val data: T) : ResultDomain<T>()
    data class Error(val exception: Throwable) : ResultDomain<Nothing>()
    data object Loading : ResultDomain<Nothing>()
}