package kr.co.are.searchcocktail.feature.favorite.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity

@Stable
sealed interface FavoriteUiState {

    @Immutable
    data object Loading : FavoriteUiState

    @Immutable
    data class Error(val isNetwork: Boolean) : FavoriteUiState

    @Immutable
    data class Success(val drinks: List<DrinkInfoEntity>, val isDefault: Boolean) : FavoriteUiState
}