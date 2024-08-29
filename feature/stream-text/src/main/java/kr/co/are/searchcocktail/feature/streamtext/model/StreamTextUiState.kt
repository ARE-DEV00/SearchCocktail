package kr.co.are.searchcocktail.feature.streamtext.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kr.co.are.searchcocktail.domain.entity.streamtext.StreamTextInfoEntity

@Stable
sealed interface StreamTextUiState {

    @Immutable
    data object Loading : StreamTextUiState

    @Immutable
    data class Error(val isNetwork: Boolean) : StreamTextUiState

    @Immutable
    data class Success(val streamTextInfo: StreamTextInfoEntity?, val playTime:Float) : StreamTextUiState
}