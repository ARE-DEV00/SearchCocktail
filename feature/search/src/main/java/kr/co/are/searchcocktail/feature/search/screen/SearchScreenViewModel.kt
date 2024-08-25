package kr.co.are.searchcocktail.feature.search.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.usecase.GetListCocktailByQueryUseCase
import kr.co.are.searchcocktail.domain.usecase.GetListCocktailFilterByAlcoholicUseCase
import kr.co.are.searchcocktail.feature.search.model.SearchUiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getListCocktailFilterByAlcoholicUseCase: GetListCocktailFilterByAlcoholicUseCase,
    private val getListCocktailByQueryUseCase: GetListCocktailByQueryUseCase,
) : ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val searchUiState = _searchUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        viewModelScope.launch {
            loadDefaultDrinks()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            loadDefaultDrinks()
        } else {
            loadSearchDrinks(query)
        }
    }

    private fun loadDefaultDrinks() {
        viewModelScope.launch {
            getListCocktailFilterByAlcoholicUseCase()
                .catch {
                    _searchUiState.value = SearchUiState.Error(isNetwork = false)
                }.collect {
                    when (it) {
                        is ResultDomain.Error -> {
                            Timber.e(it.exception)
                            _searchUiState.value = SearchUiState.Error(isNetwork = it.isNetwork)
                        }

                        ResultDomain.Loading -> {
                            _searchUiState.value = SearchUiState.Loading
                        }

                        is ResultDomain.Success -> {
                            _searchUiState.value =
                                SearchUiState.Success(it.data, isDefault = true)
                        }
                    }
                }
        }
    }

    private fun loadSearchDrinks(query: String) {
        viewModelScope.launch {
            getListCocktailByQueryUseCase(query, isRefresh = (query.length == 1))
                .catch {
                    _searchUiState.value = SearchUiState.Error(isNetwork = false)
                }.collectLatest { resultDomain ->
                    when (resultDomain) {
                        is ResultDomain.Error -> {
                            _searchUiState.value =
                                SearchUiState.Error(isNetwork = resultDomain.isNetwork)
                        }

                        ResultDomain.Loading -> {
                            _searchUiState.value = SearchUiState.Loading
                        }

                        is ResultDomain.Success -> {
                            _searchUiState.value =
                                SearchUiState.Success(resultDomain.data, isDefault = false)
                        }
                    }
                }
        }
    }

}