package kr.co.are.searchcocktail.feature.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.are.searchcocktail.feature.search.component.SearchTextField
import kr.co.are.searchcocktail.feature.search.model.SearchUiState
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF0F0F0))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchTextField(
                    text = searchQuery,
                    hint = "검색어를 입력해주세요.",
                    onTextChanged = {
                        viewModel.updateSearchQuery(it)
                    }, modifier = Modifier
                        .weight(1f)
                )

                /*Button(onClick = {}, modifier = Modifier.padding(5.dp)) {
                    Text(text = "검색")
                }*/
            }

        }
        when(val uiState = searchUiState){
            is SearchUiState.Loading -> {
                SearchLoading()
            }
            is SearchUiState.Error -> {
                Timber.d("isNetwork : ${uiState.isNetwork}")
                if(uiState.isNetwork){
                    SearchNetworkError()
                }else{
                    SearchError()
                }
            }
            is SearchUiState.Success -> {
                if(uiState.isDefault){
                    Text(text = "검색어 X")
                }else{
                    Text(text = "검색어 O")
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