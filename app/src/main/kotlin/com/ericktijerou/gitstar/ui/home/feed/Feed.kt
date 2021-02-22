package com.ericktijerou.gitstar.ui.home.feed

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.home.feed.repository.RepositoryList
import com.ericktijerou.gitstar.ui.home.feed.user.UserList
import com.ericktijerou.gitstar.ui.theme.GitstarTheme

@Composable
fun Feed(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    HomeCategoryTabs(
        navController = navController,
        categories = listOf(FeedTabs.User, FeedTabs.Repository),
    )
    NavHost(navController, startDestination = FeedTabs.User.route) {
        composable(FeedTabs.User.route) { UserList(modifier) }
        composable(FeedTabs.Repository.route) { RepositoryList(modifier) }
    }
}

@Composable
private fun HomeCategoryTabs(
    navController: NavHostController,
    categories: List<FeedTabs>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE) ?: FeedTabs.User.route
    val selectedIndex = categories.indexOfFirst { it.route == currentRoute }
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        backgroundColor = GitstarTheme.colors.iconPrimary
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    if (currentRoute != category.route) {
                        navController.navigate(category.route)
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = category.resourceId),
                        style = MaterialTheme.typography.body2
                    )
                }
            )
        }
    }
}

sealed class FeedTabs(val route: String, @StringRes val resourceId: Int) {
    object User : FeedTabs("user", R.string.home_user_list)
    object Repository : FeedTabs("repository", R.string.home_repo_list)
}
