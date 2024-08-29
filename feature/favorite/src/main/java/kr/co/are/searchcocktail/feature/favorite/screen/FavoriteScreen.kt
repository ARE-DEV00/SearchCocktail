package kr.co.are.searchcocktail.feature.favorite.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.are.searchcocktail.core.navigation.component.AppHeaderScreen
import kr.co.are.searchcocktail.feature.favorite.component.FavoriteCocktailListView
import kr.co.are.searchcocktail.feature.favorite.model.FavoriteUiState
import timber.log.Timber

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onTabItem: (drinkInfo: String) -> Unit,
    onTabBack: () -> Unit
) {
    val favoriteUiState by viewModel.favoriteUiState.collectAsStateWithLifecycle()

    SideEffect {
        if (favoriteUiState is FavoriteUiState.Success) {
            viewModel.loadFavoriteCocktail()
        }
    }

    AppHeaderScreen(
        headerTitle = "Favorite",
        leftIconImageVector = Icons.AutoMirrored.Filled.ArrowBack,
        modifier = Modifier.fillMaxSize(),
        onTabLeftIcon = {
            onTabBack()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (val uiState = favoriteUiState) {
                    is FavoriteUiState.Loading -> {
                        SearchLoading()
                    }

                    is FavoriteUiState.Error -> {
                        Timber.d("isNetwork : ${uiState.isNetwork}")
                        if (uiState.isNetwork) {
                            SearchNetworkError()
                        } else {
                            SearchError()
                        }
                    }

                    is FavoriteUiState.Success -> {
                        if (uiState.drinks.isNotEmpty()) {
                            FavoriteCocktailListView(
                                itemList = uiState.drinks,
                                onTabImage = { drinkInfo ->
                                    Timber.d("onTabImage:$drinkInfo")
                                    onTabItem(drinkInfo.id)
                                }
                            )
                        } else {
                            FavoriteEmptyList()
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun SearchLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SearchError() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "오류가 발생하였습니다.")
    }
}

@Composable
private fun SearchNetworkError() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "인터넷 연결을 확인해주세요.")
    }
}

@Composable
private fun FavoriteEmptyList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "즐겨찾기가 없습니다.")
    }
}