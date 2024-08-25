package kr.co.are.searchcocktail.navigation


import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.co.are.searchcocktail.feature.search.navigation.searchNavGraph


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val slideInOut = fadeIn() + slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(300)
    )

    NavHost(navController = navController, startDestination = "search",
        enterTransition = { slideInOut },
        popEnterTransition = { slideInOut }) {
        searchNavGraph()


    }
}