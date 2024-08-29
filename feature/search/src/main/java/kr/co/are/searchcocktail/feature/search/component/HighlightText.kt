package kr.co.are.searchcocktail.feature.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HighlightText(
    text: String,
    highlight: String
) {
    if (highlight.isEmpty() || !text.contains(highlight, ignoreCase = true)) {
        Text(text = text)
        return
    }

    Row {
        val regex = "(?i)($highlight)".toRegex()
        val parts = regex.findAll(text)
        var currentIndex = 0 // 현재까지 처리한 문자열 인덱스

        parts.forEach { matchResult ->  // 정규식에 매칭된 문자열 위치 정보를 가지고 처리
            val startIndex = matchResult.range.first
            val endIndex = matchResult.range.last + 1
            if (currentIndex < startIndex) {
                Text(text = text.substring(currentIndex, startIndex))
            }

            Box(
                modifier = Modifier.background(Color(0xFFADD8E6))
            ) {
                Text(
                    text = text.substring(startIndex, endIndex),
                    fontWeight = FontWeight.Bold,
                )
            }

            currentIndex = endIndex
        }

        if (currentIndex < text.length) {
            Text(
                text = text.substring(currentIndex)
            )
        }
    }
}