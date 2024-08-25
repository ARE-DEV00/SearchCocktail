package kr.co.are.searchcocktail.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.are.searchcocktail.feature.search.screen.SearchScreen

fun NavController.navigateSearch() {
    navigate("search")
}

fun NavGraphBuilder.searchNavGraph(
) {
    composable("search") {
        SearchScreen()
    }
}