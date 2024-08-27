package kr.co.are.searchcocktail.core.navigation


sealed interface Route {
    val path: String

    data object Search : Route {
        override val path: String = "search"
    }

    data object Detail : Route {
        override val path: String = "detail"
    }

    data object StreamText : Route {
        override val path: String = "streamText"
    }
}