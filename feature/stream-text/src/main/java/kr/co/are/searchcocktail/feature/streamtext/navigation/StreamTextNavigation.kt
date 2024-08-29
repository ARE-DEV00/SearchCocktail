package kr.co.are.searchcocktail.feature.streamtext.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kr.co.are.searchcocktail.core.navigation.Route.StreamText
import kr.co.are.searchcocktail.feature.streamtext.screen.StreamTextScreen

fun NavController.navigateStreamText() {
    navigate(StreamText.path)
}

fun NavGraphBuilder.streamTextNavGraph(
    onTabBack: () -> Unit
) {
    composable(route = StreamText.path) {
        StreamTextScreen(onTabBack = onTabBack)
    }
}