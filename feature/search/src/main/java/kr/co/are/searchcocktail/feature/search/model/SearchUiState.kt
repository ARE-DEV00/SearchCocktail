package kr.co.are.searchcocktail.feature.search.model

import android.net.Network
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity

@Stable
sealed interface SearchUiState {

    @Immutable
    data object Loading : SearchUiState

    @Immutable
    data class Error(val isNetwork: Boolean) : SearchUiState

    @Immutable
    data class Success(val drinks: List<DrinkInfoEntity>, val isDefault: Boolean) : SearchUiState
}