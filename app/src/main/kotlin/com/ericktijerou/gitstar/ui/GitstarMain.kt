package com.ericktijerou.gitstar.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.gitstar.ui.home.Home
import com.ericktijerou.gitstar.ui.theme.GitstarTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun GitstartMain() {
    ProvideWindowInsets {
        GitstarTheme {
            val navController = rememberNavController()
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { Home() }
            }
        }
    }
}
