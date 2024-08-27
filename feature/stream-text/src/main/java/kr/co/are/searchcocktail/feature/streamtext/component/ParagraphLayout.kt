package kr.co.are.searchcocktail.feature.streamtext.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ParagraphLayout(
    modifier: Modifier = Modifier,
    spacing: Dp = 1.dp, // 텍스트 컴포넌트 간의 간격
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        // 각 텍스트 컴포넌트를 측정
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // 전체 높이 및 너비를 계산
        var currentX = 0
        var currentY = 0
        var rowMaxHeight = 0

        placeables.forEach { placeable ->
            if (currentX + placeable.width > constraints.maxWidth) {
                // 다음 줄로 이동
                currentX = 0
                currentY += rowMaxHeight + spacing.roundToPx()
                rowMaxHeight = 0
            }

            currentX += placeable.width
            rowMaxHeight = maxOf(rowMaxHeight, placeable.height)
        }

        // 전체 레이아웃의 크기 설정
        layout(constraints.maxWidth, currentY + rowMaxHeight) {
            currentX = 0
            currentY = 0
            rowMaxHeight = 0

            placeables.forEach { placeable ->
                if (currentX + placeable.width > constraints.maxWidth) {
                    // 다음 줄로 이동
                    currentX = 0
                    currentY += rowMaxHeight + spacing.roundToPx()
                    rowMaxHeight = 0
                }

                placeable.placeRelative(x = currentX, y = currentY)
                currentX += placeable.width
                rowMaxHeight = maxOf(rowMaxHeight, placeable.height)
            }
        }
    }
}