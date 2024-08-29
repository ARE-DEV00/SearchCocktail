package kr.co.are.searchcocktail.feature.favorite.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.domain.usecase.GetListFavoriteCocktailUseCase
import kr.co.are.searchcocktail.feature.favorite.model.FavoriteUiState
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private  val getListFavoriteCocktailUseCase: GetListFavoriteCocktailUseCase
) : ViewModel() {

    private val _favoriteUiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val favoriteUiState = _favoriteUiState.asStateFlow()


    init {
        loadFavoriteCocktail()
    }

    fun loadFavoriteCocktail() {
        viewModelScope.launch {
            getListFavoriteCocktailUseCase().collect { resultDomain ->
                when (resultDomain) {
                    is kr.co.are.searchcocktail.domain.model.ResultDomain.Success -> {
                        _favoriteUiState.value = FavoriteUiState.Success(resultDomain.data, false)
                    }
                    is kr.co.are.searchcocktail.domain.model.ResultDomain.Error -> {
                        _favoriteUiState.value = FavoriteUiState.Error(resultDomain.isNetwork)
                    }
                    kr.co.are.searchcocktail.domain.model.ResultDomain.Loading -> {
                        _favoriteUiState.value = FavoriteUiState.Loading
                    }
                }
            }
        }
    }



}