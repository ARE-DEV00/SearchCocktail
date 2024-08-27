package kr.co.are.searchcocktail.feature.detail.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.feature.detail.model.DetailUiState
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    init {
        viewModelScope.launch {

        }
    }
}