package kr.co.are.searchcocktail.feature.detail.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.usecase.AddFavoriteCocktailUseCase
import kr.co.are.searchcocktail.domain.usecase.DeleteFavoriteCocktailByIdUseCase
import kr.co.are.searchcocktail.domain.usecase.GetCocktailByIdUseCase
import kr.co.are.searchcocktail.feature.detail.model.DetailUiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val addFavoriteCocktailUseCase: AddFavoriteCocktailUseCase,
    private val deleteFavoriteCocktailByIdUseCase: DeleteFavoriteCocktailByIdUseCase
) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    fun loadCocktailById(id: String?) {
        if(id == null){
            _detailUiState.value = DetailUiState.Error(false)
            return
        }

        viewModelScope.launch {
            getCocktailByIdUseCase(id)
                .collect { resultDomain ->
                    when (resultDomain) {
                        is ResultDomain.Success -> {
                            if(resultDomain.data != null){
                                _detailUiState.value = DetailUiState.Success(resultDomain.data!!)
                            }else{
                                _detailUiState.value = DetailUiState.Error(false)
                            }
                        }
                        is ResultDomain.Error -> {
                            _detailUiState.value = DetailUiState.Error(resultDomain.isNetwork)
                        }
                        ResultDomain.Loading -> {
                            _detailUiState.value = DetailUiState.Loading
                        }
                    }
                }
        }
    }

    fun updateFavorite(drinkInfo: DrinkInfoEntity) {
        if (drinkInfo.isFavorite) {
            deleteFavorite(drinkInfo)
        } else {
            addFavorite(drinkInfo)
        }
    }

    private fun addFavorite(drinkInfo: DrinkInfoEntity) {
        viewModelScope.launch {
            addFavoriteCocktailUseCase(drinkInfo)
                .catch {
                    Timber.e(it)
                }.collectLatest { resultDomain ->
                    when (resultDomain) {
                        is ResultDomain.Error -> {
                            Timber.e(resultDomain.exception)
                        }

                        ResultDomain.Loading -> {
                            Timber.d("Loading")
                        }

                        is ResultDomain.Success -> {
                            Timber.d("Success")
                        }
                    }
                }
        }
    }

    private fun deleteFavorite(drinkInfo: DrinkInfoEntity) {
        viewModelScope.launch {
            deleteFavoriteCocktailByIdUseCase(drinkInfo)
                .catch {
                    Timber.e(it)
                }.collectLatest { resultDomain ->
                    when (resultDomain) {
                        is ResultDomain.Error -> {
                            Timber.e(resultDomain.exception)
                        }

                        ResultDomain.Loading -> {
                            Timber.d("Loading")
                        }

                        is ResultDomain.Success -> {
                            Timber.d("Success")
                        }
                    }
                }
        }
    }


}