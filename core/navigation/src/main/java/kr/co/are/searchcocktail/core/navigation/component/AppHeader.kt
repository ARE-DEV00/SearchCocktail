package kr.co.are.searchcocktail.core.navigation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun AppHeader(
    headerTitle: String,
    leftIconImageVector: ImageVector? = null,
    leftIconImageVectorColor: Color = Color.Black,
    rightIconImageVector: ImageVector? = null,
    rightIconImageVectorColor: Color = Color.Black,
    onTabRightIcon: (() -> Unit)? = null,
    onTabLeftIcon: (() -> Unit)? = null,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {

        if (leftIconImageVector != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                val coroutineScope = rememberCoroutineScope()
                var lastClickTime by remember { mutableStateOf(0L) }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastClickTime > 1000) {
                                lastClickTime = currentTime
                                onTabLeftIcon?.invoke()
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = leftIconImageVector,
                        contentDescription = null,
                        tint = leftIconImageVectorColor
                    )
                }
            }
        }


        Text(
            text = headerTitle,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        if (rightIconImageVector != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
            ) {
                val coroutineScope = rememberCoroutineScope()
                var lastClickTime by remember { mutableStateOf(0L) }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastClickTime > 1000) {
                                lastClickTime = currentTime
                                onTabRightIcon?.invoke()
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = rightIconImageVector,
                        contentDescription = null,
                        tint = rightIconImageVectorColor
                    )
                }
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun AppHeaderPreview() {
    AppHeader(
        headerTitle = "TEST",
        leftIconImageVector = Icons.Default.ArrowBack,
        leftIconImageVectorColor = Color.Black,
        rightIconImageVector = Icons.Default.Favorite,
        rightIconImageVectorColor = Color.Red
    )
}
