package kr.co.are.searchcocktail.feature.search.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    Text("SearchScreen")
}