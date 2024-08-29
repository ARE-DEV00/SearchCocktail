package kr.co.are.searchcocktail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import dagger.hilt.android.AndroidEntryPoint
import kr.co.are.searchcocktail.navigation.AppNavigation
import kr.co.are.searchcocktail.ui.theme.SearchCocktailTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SearchCocktailTheme {
                Scaffold {
                    AppNavigation()
                }

            }
        }
    }
}