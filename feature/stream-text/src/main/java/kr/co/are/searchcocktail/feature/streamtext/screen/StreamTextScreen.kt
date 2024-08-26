package kr.co.are.searchcocktail.feature.streamtext.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.are.searchcocktail.core.youtubeplayer.component.YoutubePlayer
import timber.log.Timber

@Composable
fun StreamTextScreen(
    viewModel: StreamTextViewModel = hiltViewModel()
) {
    YoutubePlayer(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        bridgeName = "SearchCocktail",
        videoId = "M7lc1UVf-VE",
        onPlayTimeUpdated = { time ->
            Timber.d("#### onPlayTimeUpdated-${time}")
        }
    )
}