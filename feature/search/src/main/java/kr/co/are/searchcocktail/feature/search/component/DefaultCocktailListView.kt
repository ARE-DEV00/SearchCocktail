package kr.co.are.searchcocktail.feature.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity

@Composable
fun DefaultCocktailListView(
    itemList: List<DrinkInfoEntity>,
    onTabImage: (id: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(
            itemList.size,
        ) { item ->
            Box {
                AsyncImage(
                    model = itemList[item].thumbUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                )

                if (itemList[item].isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(25.dp, 25.dp)
                            .padding(top = 5.dp, end = 5.dp),
                        tint = Color.Red,
                    )
                }

                if (itemList[item].name != null) {
                    Text(
                        text = itemList[item].name!!,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(5.dp),
                        color = Color.White
                    )

                }

            }
        }
    }
}