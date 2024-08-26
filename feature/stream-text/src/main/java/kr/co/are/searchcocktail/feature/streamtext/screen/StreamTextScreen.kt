package kr.co.are.searchcocktail.feature.streamtext.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StreamTextScreen(
    viewModel: StreamTextViewModel = hiltViewModel()
) {
    Box {
        Text(text = "StreamTextScreen")
    }
}

@Preview(name = "StreamTextScreen")
@Composable
private fun PreviewStreamTextScreen() {
    StreamTextScreen()
}