package kr.co.are.searchcocktail.feature.detail.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity

@Stable
sealed interface DetailUiState {

    @Immutable
    data object Loading : DetailUiState

    @Immutable
    data class Error(val isNetwork: Boolean) : DetailUiState

    @Immutable
    data class Success(val drinkInfo: DrinkInfoEntity) : DetailUiState
}