package kr.co.are.searchcocktail.feature.favorite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.are.searchcocktail.core.navigation.Route
import kr.co.are.searchcocktail.feature.favorite.screen.FavoriteScreen

fun NavController.navigateFavorite(id: String) {
    navigate(Route.Favorite.path)
}

fun NavGraphBuilder.favoriteNavGraph(
    onTabItem: (String) -> Unit,
    onTabBack: () -> Unit
) {
    composable(
        route = Route.Favorite.path,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        FavoriteScreen(onTabItem = onTabItem, onTabBack = onTabBack)
    }
}