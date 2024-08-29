package kr.co.are.searchcocktail.core.navigation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AppHeaderScreen(
    headerTitle: String,
    rightIconImageVector: ImageVector? = null,
    rightIconImageVectorColor: Color = Color.Black,
    leftIconImageVector: ImageVector? = null,
    leftIconImageVectorColor: Color = Color.Black,
    onTabRightIcon: (() -> Unit)? = null,
    onTabLeftIcon: (() -> Unit)? = null,
    modifier: Modifier,
    composable: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppHeader(
            headerTitle = headerTitle,
            leftIconImageVector = leftIconImageVector,
            leftIconImageVectorColor = leftIconImageVectorColor,
            rightIconImageVector = rightIconImageVector,
            rightIconImageVectorColor = rightIconImageVectorColor,
            onTabRightIcon = onTabRightIcon,
            onTabLeftIcon = onTabLeftIcon
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFE0E0E0)
        )

        composable()

    }

}