package kr.co.are.searchcocktail.feature.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kr.co.are.searchcocktail.core.navigation.component.AppHeaderScreen
import kr.co.are.searchcocktail.domain.entity.drink.DrinkInfoEntity
import kr.co.are.searchcocktail.feature.detail.model.DetailUiState

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    id: String? = null,
    onTabYoutube: (String) -> Unit,
    onTabBack: () -> Unit
) {
    LaunchedEffect(key1 = id) {
        viewModel.loadCocktailById(id = id)
    }

    val detailUiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    val uiState = detailUiState

    AppHeaderScreen(
        headerTitle = if (uiState is DetailUiState.Success) uiState.drinkInfo.name ?: "" else "",
        leftIconImageVector = Icons.AutoMirrored.Filled.ArrowBack,
        modifier = Modifier.fillMaxSize(),
        onTabLeftIcon = {
            onTabBack()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is DetailUiState.Loading -> DetailLoading()
                is DetailUiState.Error -> DetailError()
                is DetailUiState.Success -> {
                    DetailSuccess(uiState)
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)  // Align the FAB to the bottom end
                            .padding(16.dp),
                        onClick = {
                            onTabYoutube(uiState.drinkInfo.id)
                        }) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.Red,
                        )
                    }
                }
            }
        }

    }

}

@Composable
private fun DetailLoading() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DetailError() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "오류가 발생하였습니다.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun DetailSuccess(
    uiState: DetailUiState.Success,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val drinkInfo = uiState.drinkInfo

    var isFavorite by remember(drinkInfo.id) { mutableStateOf(drinkInfo.isFavorite) }
    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 칵테일 이미지
            AsyncImage(
                model = drinkInfo.thumbUrl,
                contentDescription = drinkInfo.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 칵테일 이름
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                drinkInfo.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                FavoriteButton(isFavorite = isFavorite, onClick = {
                    isFavorite = !isFavorite
                    viewModel.updateFavorite(drinkInfo)
                    drinkInfo.isFavorite = isFavorite
                })
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 칵테일 설명
            InstructionList(drinkInfo)

            Spacer(modifier = Modifier.height(16.dp))

            // 칵테일의 세부 정보
            DetailInfoSection(
                category = drinkInfo.category,
                alcoholic = drinkInfo.alcoholic,
                glass = drinkInfo.glass
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 재료 및 측정 단위
            IngredientList(drinkInfo)
        }
    }
}


@Composable
private fun DetailInfoSection(category: String?, alcoholic: String?, glass: String?) {
    val detailInfoList = listOfNotNull(
        "Category" to category,
        "Alcoholic" to alcoholic,
        "Glass" to glass,
    ).filter { (it.second?.isNotEmpty() == true) }

    if (detailInfoList.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "[Information]",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            detailInfoList.forEach { (title, detail) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title, style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = detail!!, style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
    }
}

@Composable
private fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}

@Composable
private fun InstructionList(drinkInfo: DrinkInfoEntity) {
    val instructionList = listOfNotNull(
        "En" to drinkInfo.instructions,
        "Es" to drinkInfo.instructionsEs,
        "De" to drinkInfo.instructionsDe,
        "Fr" to drinkInfo.instructionsFr,
        "It" to drinkInfo.instructionsIt,
        "Zh-Hans" to drinkInfo.instructionsZhHans,
        "Zh-Hant" to drinkInfo.instructionsZhHant
    ).filter { (it.second?.isNotEmpty() == true) }

    if (instructionList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "[Instruction]",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            instructionList.forEach { (language, instruction) ->
                Text(
                    text = language,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = instruction!!, style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Composable
private fun IngredientList(drinkInfo: DrinkInfoEntity) {
    val ingredientList = listOfNotNull(
        drinkInfo.ingredient1 to drinkInfo.measure1,
        drinkInfo.ingredient2 to drinkInfo.measure2,
        drinkInfo.ingredient3 to drinkInfo.measure3,
        drinkInfo.ingredient4 to drinkInfo.measure4,
        drinkInfo.ingredient5 to drinkInfo.measure5,
        drinkInfo.ingredient6 to drinkInfo.measure6,
        drinkInfo.ingredient7 to drinkInfo.measure7,
        drinkInfo.ingredient8 to drinkInfo.measure8,
        drinkInfo.ingredient9 to drinkInfo.measure9,
        drinkInfo.ingredient10 to drinkInfo.measure10,
        drinkInfo.ingredient11 to drinkInfo.measure11,
        drinkInfo.ingredient12 to drinkInfo.measure12,
        drinkInfo.ingredient13 to drinkInfo.measure13,
        drinkInfo.ingredient14 to drinkInfo.measure14,
        drinkInfo.ingredient15 to drinkInfo.measure15
    ).filter { (it.first?.isNotEmpty() == true) && (it.second?.isNotEmpty() == true) }

    if (ingredientList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "[Ingredient]",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            ingredientList.forEach { (ingredient, measure) ->
                Text(
                    text = "$ingredient: $measure", style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}