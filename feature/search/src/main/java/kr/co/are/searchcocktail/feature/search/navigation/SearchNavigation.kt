package kr.co.are.searchcocktail.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.are.searchcocktail.feature.search.screen.SearchScreen
import kr.co.are.searchcocktail.core.navigation.Route.Search

fun NavController.navigateSearch() {
    navigate(Search.path)
}

fun NavGraphBuilder.searchNavGraph(
) {
    composable(route = Search.path) {
        SearchScreen()
    }
}