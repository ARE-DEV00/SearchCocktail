package kr.co.are.searchcocktail.navigation


import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kr.co.are.searchcocktail.core.navigation.Route
import kr.co.are.searchcocktail.feature.detail.navigation.detailNavGraph
import kr.co.are.searchcocktail.feature.detail.navigation.navigateDetail
import kr.co.are.searchcocktail.feature.search.navigation.searchNavGraph
import kr.co.are.searchcocktail.feature.streamtext.navigation.streamTextNavGraph
import timber.log.Timber


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val slideInOut = fadeIn() + slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(300)
    )

    NavHost(navController = navController, startDestination = Route.Search.path,
        enterTransition = { slideInOut },
        popEnterTransition = { slideInOut }) {

        searchNavGraph(onTabItem = {
            Timber.d("onTabItem: $it")
            navController.navigateDetail(it)
        })

        streamTextNavGraph()

        detailNavGraph()

    }
}