package kr.co.are.searchcocktail.feature.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity

@Composable
fun SearchCocktailListView(
    itemList: List<DrinkInfoEntity>,
    onTabFavorite: (drinkInfo: DrinkInfoEntity) -> Unit,
    onTabItem: (drinkInfo: DrinkInfoEntity) -> Unit
) {
    LazyColumn {
        items(itemList.size, key = { itemList[it].id }) { item ->
            val drinkInfo = itemList[item]
            var isFavorite by remember(drinkInfo.id) { mutableStateOf(drinkInfo.isFavorite) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        onTabItem(drinkInfo)
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(drinkInfo.thumbUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = drinkInfo.name ?: "",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = drinkInfo.category ?: "",
                        color = Color.Gray
                    )
                }

                IconButton(onClick = {
                    onTabFavorite(drinkInfo)
                    isFavorite = !isFavorite
                    drinkInfo.isFavorite = isFavorite
                }) {
                    Icon(
                        imageVector = if (isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}
