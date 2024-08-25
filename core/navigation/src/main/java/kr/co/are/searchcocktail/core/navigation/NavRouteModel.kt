package kr.co.are.searchcocktail.core.navigation


sealed interface Route {
    val path: String

    data object Search : Route{
        override val path: String = "search"
    }

}