package kr.co.are.searchcocktail.feature.detail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.are.searchcocktail.feature.detail.model.DetailUiState

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    id: String,
) {
    viewModel.loadCocktailById(id = id)
    
    val detailUiState by viewModel.detailUiState.collectAsStateWithLifecycle()

    when (val uiState = detailUiState) {
        is DetailUiState.Loading -> {
            DetailLoading()
        }

        is DetailUiState.Error -> {
            DetailError()
        }

        is DetailUiState.Success -> {
            Text(text = "DetailScreen")
        }
    }
}

@Composable
private fun DetailLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DetailError() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "오류가 발생하였습니다.")
    }
}

