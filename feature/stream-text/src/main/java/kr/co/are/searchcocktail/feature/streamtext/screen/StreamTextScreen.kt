package kr.co.are.searchcocktail.feature.streamtext.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.are.searchcocktail.core.youtubeplayer.component.YoutubePlayer
import kr.co.are.searchcocktail.domain.entity.streamtext.ParagraphChildEntity
import kr.co.are.searchcocktail.feature.streamtext.model.StreamTextUiState
import timber.log.Timber

@Composable
fun StreamTextScreen(
    viewModel: StreamTextViewModel = hiltViewModel()
) {
    val streamTextUiState by viewModel.streamTextUiState.collectAsStateWithLifecycle()

    Column {
        YoutubePlayer(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            bridgeName = "SearchCocktail",
            videoId = "Dd1N9NrPt3A",
            onPlayTimeUpdated = { time ->
                Timber.d("#### onPlayTimeUpdated-${time}")
                viewModel.updatePlayTime(time)
            }
        )

        when (val uiState = streamTextUiState) {
            is StreamTextUiState.Loading -> {
                Text(text = "로딩 중")
            }

            is StreamTextUiState.Error -> {
                Text(text = "에러 발생")
            }

            is StreamTextUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    uiState.streamTextInfo?.lexicalEntity?.editorState?.root?.children?.let { children ->
                        items(children) { item ->
                            item.children.forEach { child ->
                                when (child) {
                                    is ParagraphChildEntity.KaraokeEntity -> {
                                        val formattedNumber = "%.1f".format(uiState.playTime)
                                        val isHighlighted = child.s <= formattedNumber.toDouble() && formattedNumber.toDouble() <= child.e
                                        KaraokeText(child, isHighlighted)
                                    }
                                    is ParagraphChildEntity.SpeakerBlockEntity -> {

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KaraokeText(child: ParagraphChildEntity.KaraokeEntity, isHighlighted: Boolean) {
    Box(
        modifier = Modifier
            .background(if (isHighlighted) Color(0xFFADD8E6) else Color.Transparent)
            .padding(4.dp)
    ) {
        Text(
            text = child.text,
            style = if (isHighlighted) TextStyle(background = Color(0xFFADD8E6)) else TextStyle()
        )
    }
}
