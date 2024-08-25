package kr.co.are.searchcocktail.feature.search.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.domain.usecase.GetFilterByAlcoholicUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getFilterByAlcoholicUseCase: GetFilterByAlcoholicUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getFilterByAlcoholicUseCase().catch {
                Timber.e(it)
            }.collectLatest {
                Timber.d("##### getFilterByAlcoholicUseCase - $it")
            }
        }
    }

}