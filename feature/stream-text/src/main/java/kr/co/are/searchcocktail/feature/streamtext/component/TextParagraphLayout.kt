package kr.co.are.searchcocktail.feature.streamtext.component

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextParagraphLayout(
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()

    ParagraphLayout(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        content()
    }
}
