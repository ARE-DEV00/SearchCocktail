package kr.co.are.searchcocktail.feature.streamtext.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.are.searchcocktail.core.youtubeplayer.component.YoutubePlayer
import kr.co.are.searchcocktail.domain.entity.streamtext.ParagraphChildEntity
import kr.co.are.searchcocktail.feature.streamtext.component.KaraokeText
import kr.co.are.searchcocktail.feature.streamtext.component.ParagraphLayout
import kr.co.are.searchcocktail.feature.streamtext.component.TextParagraphLayout
import kr.co.are.searchcocktail.feature.streamtext.model.StreamTextUiState
import timber.log.Timber

@Composable
fun StreamTextScreen(
    viewModel: StreamTextViewModel = hiltViewModel()
) {
    val streamTextUiState by viewModel.streamTextUiState.collectAsStateWithLifecycle()



    when (val uiState = streamTextUiState) {
        is StreamTextUiState.Loading -> {
            StreamTextLoading()
        }

        is StreamTextUiState.Error -> {
            StreamTextError()
        }

        is StreamTextUiState.Success -> {
            Column {
                YoutubePlayer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    bridgeName = "SearchCocktail",
                    videoId = uiState.streamTextInfo?.videoUrl ?: "",
                    onPlayTimeUpdated = { time ->
                        Timber.d("#### onPlayTimeUpdated-${time}")
                        viewModel.updatePlayTime(time)
                    }
                )
                TextParagraphLayout {
                    uiState.streamTextInfo?.lexicalEntity?.editorState?.root?.children?.let { children ->
                        children.forEach { child ->
                            child.children.forEach {
                                when (it) {
                                    is ParagraphChildEntity.SpeakerBlockEntity -> {

                                    }

                                    is ParagraphChildEntity.KaraokeEntity -> {
                                        KaraokeText(
                                            it,
                                            isHighlighted = uiState.playTime >= it.s && uiState.playTime <= it.e,
                                            onTabText = { startTime, endTime ->
                                                Timber.d("#### onTabText-:${startTime}-${endTime}")
                                            }
                                        )
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
private fun StreamTextLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun StreamTextError() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "오류가 발생하였습니다.")
    }
}

