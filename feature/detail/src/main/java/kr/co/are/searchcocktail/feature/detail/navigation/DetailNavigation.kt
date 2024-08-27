package kr.co.are.searchcocktail.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.are.searchcocktail.core.navigation.Route
import kr.co.are.searchcocktail.feature.detail.screen.DetailScreen

fun NavController.navigateDetail(id:String) {
    navigate(Route.Detail.path.replace("{id}", id))
}

fun NavGraphBuilder.detailNavGraph(
    id: String? = null
) {
    composable(route = Route.Detail.path) {
        DetailScreen(id = id)
    }
}