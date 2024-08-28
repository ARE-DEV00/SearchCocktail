package kr.co.are.searchcocktail.feature.streamtext.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextParagraphLayout(
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()

    FlowRow(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        content()
    }
}
