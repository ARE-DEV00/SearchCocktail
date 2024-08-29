package kr.co.are.searchcocktail.feature.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.feature.search.component.DefaultCocktailListView
import kr.co.are.searchcocktail.feature.search.component.SearchCocktailListView
import kr.co.are.searchcocktail.feature.search.component.SearchTextField
import kr.co.are.searchcocktail.feature.search.model.SearchUiState
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    onTabItem: (drinkInfo: String) -> Unit,
    onTabFavorites: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF0F0F0))
                .pointerInput(Unit) {
                    // 터치 시 키보드 숨기기
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchTextField(
                        modifier = Modifier
                            .weight(1f),
                        text = searchQuery,
                        hint = "검색어를 입력해주세요.",
                        onTextChanged = {
                            viewModel.updateSearchQuery(it)
                        },
                        onTextClear = {
                            focusManager.clearFocus()
                            viewModel.updateSearchQuery("")
                        }
                    )
                }

            }
            when (val uiState = searchUiState) {
                is SearchUiState.Loading -> {
                    SearchLoading()
                }

                is SearchUiState.Error -> {
                    Timber.d("isNetwork : ${uiState.isNetwork}")
                    if (uiState.isNetwork) {
                        SearchNetworkError()
                    } else {
                        SearchError()
                    }
                }

                is SearchUiState.Success -> {
                    if (uiState.isDefault) {
                        if (uiState.drinks.isNotEmpty()) {
                            DefaultCocktailListView(
                                itemList = uiState.drinks,
                                onTabImage = { drinkInfo ->
                                    Timber.d("onTabImage:$drinkInfo")
                                    onTabItem(drinkInfo.id)
                                }
                            )
                        } else {
                            DefaultEmptyList()
                        }

                    } else {
                        if (uiState.drinks.isNotEmpty()) {
                            SearchCocktailListView(
                                searchQuery = searchQuery,
                                itemList = uiState.drinks,
                                onTabFavorite = { id ->
                                    Timber.d("onTabFavorite:$id")
                                    viewModel.updateFavorite(id)
                                },
                                onTabItem = { drinkInfo ->
                                    Timber.d("onTabItem:$drinkInfo")
                                    onTabItem(drinkInfo.id)
                                })
                        } else {
                            SearchEmptyList()
                        }
                    }

                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                onTabFavorites()
            }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red,
            )
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
private fun SearchEmptyList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "검색 결과가 없습니다.")
    }
}

@Composable
private fun DefaultEmptyList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "칵테일 정보가 없습니다.")
    }
}