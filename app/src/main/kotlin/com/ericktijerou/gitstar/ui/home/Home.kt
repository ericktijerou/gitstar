package com.ericktijerou.gitstar.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.ui.component.GitstarScaffold
import com.ericktijerou.gitstar.ui.home.profile.Profile
import com.ericktijerou.gitstar.ui.home.feed.Feed
import com.ericktijerou.gitstar.ui.theme.GitstarTheme

@Composable
fun Home() {
    val navController = rememberNavController()
    GitstarScaffold(
        topBar = { HomeAppBar(Modifier.fillMaxWidth()) },
        bottomBar = {
            val sections = listOf(HomeSection.Feed, HomeSection.Profile)
            HomeBottomNavigation(navController = navController, sections = sections)
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHost(navController, startDestination = HomeSection.Feed.route) {
            composable(HomeSection.Feed.route) { Feed(modifier) }
            composable(HomeSection.Profile.route) { Profile(modifier) }
        }
    }
}

@Composable
fun HomeBottomNavigation(
    navController: NavHostController,
    sections: List<HomeSection>
) {
    BottomNavigation(
        backgroundColor = GitstarTheme.colors.iconPrimary,
        contentColor = GitstarTheme.colors.textInteractive
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        sections.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = EMPTY) },
                label = { Text(stringResource(screen.resourceId)) },
                alwaysShowLabels = false,
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = GitstarTheme.colors.iconPrimary
) {
    TopAppBar(
        title = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .preferredHeightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        modifier = modifier
    )
}

sealed class HomeSection(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Feed : HomeSection("feed", R.string.home_feed, Icons.Outlined.Home)
    object Profile : HomeSection("profile", R.string.home_profile, Icons.Outlined.AccountCircle)
}
