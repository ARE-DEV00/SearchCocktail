package kr.co.are.searchcocktail.feature.streamtext.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kr.co.are.searchcocktail.domain.entity.streamtext.ParagraphChildEntity

@Composable
fun KaraokeText(
    child: ParagraphChildEntity.KaraokeEntity,
    isHighlighted: Boolean,
    onTabText: ((startTime: Double, endTime: Double) -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .background(if (isHighlighted) Color(0xFFADD8E6) else Color.Transparent)
            .padding(4.dp)
            .clickable {
                onTabText?.invoke(child.s, child.e)
            }

    ) {
        Text(
            text = child.text,
            style = if (isHighlighted) TextStyle(background = Color(0xFFADD8E6)) else TextStyle()
        )
    }
}