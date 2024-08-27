package kr.co.are.searchcocktail.feature.detail.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.usecase.GetCocktailByIdUseCase
import kr.co.are.searchcocktail.feature.detail.model.DetailUiState
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase
) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    fun loadCocktailById(id: String?) {
        if(id == null) return

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



}