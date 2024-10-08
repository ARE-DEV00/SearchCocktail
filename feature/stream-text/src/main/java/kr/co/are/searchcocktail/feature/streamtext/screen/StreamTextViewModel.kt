package kr.co.are.searchcocktail.feature.streamtext.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kr.co.are.searchcocktail.domain.entity.streamtext.StreamTextInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.usecase.GetStreamTextByIdUseCase
import kr.co.are.searchcocktail.feature.streamtext.model.StreamTextUiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StreamTextViewModel @Inject constructor(
    private val getStreamTextByIdUseCase: GetStreamTextByIdUseCase,
) : ViewModel() {

    private val _streamTextUiState = MutableStateFlow<StreamTextUiState>(StreamTextUiState.Loading)
    val streamTextUiState = _streamTextUiState.asStateFlow()

    private var streamTextInfoEntity: StreamTextInfoEntity? = null

    init {
        viewModelScope.launch {
            loadStreamTextInfo()
        }
    }

    private fun loadStreamTextInfo() {
        viewModelScope.launch {
            getStreamTextByIdUseCase()
                .catch {
                    _streamTextUiState.value = StreamTextUiState.Error(isNetwork = false)
                }.collect {
                    when (it) {
                        is ResultDomain.Error -> {
                            Timber.e(it.exception)
                            _streamTextUiState.value =
                                StreamTextUiState.Error(isNetwork = it.isNetwork)
                        }

                        ResultDomain.Loading -> {
                            _streamTextUiState.value = StreamTextUiState.Loading
                        }

                        is ResultDomain.Success -> {
                            streamTextInfoEntity = it.data
                            _streamTextUiState.value =
                                StreamTextUiState.Success(streamTextInfoEntity, 0f)
                        }
                    }
                }
        }
    }

    fun updatePlayTime(time: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            _streamTextUiState.value = StreamTextUiState.Success(streamTextInfoEntity, time)
        }
    }
}