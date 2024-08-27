package kr.co.are.searchcocktail.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kr.co.are.searchcocktail.core.navigation.Route
import kr.co.are.searchcocktail.feature.detail.screen.DetailScreen
import timber.log.Timber

fun NavController.navigateDetail(id:String) {
    Timber.d("### navigateDetail: ${Route.Detail.path.replace("{id}", id)}")
    navigate(Route.Detail.path.replace("{id}", id))
}

fun NavGraphBuilder.detailNavGraph(
    navController: NavController
) {
    composable(
        route = Route.Detail.path,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")
        DetailScreen(id = id)
    }
}