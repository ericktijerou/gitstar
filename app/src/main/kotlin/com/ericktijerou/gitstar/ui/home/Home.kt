package com.ericktijerou.gitstar.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.ui.component.GitstarScaffold
import com.ericktijerou.gitstar.ui.home.feed.Feed
import com.ericktijerou.gitstar.ui.home.profile.Profile
import com.ericktijerou.gitstar.ui.theme.GitstarTheme
import com.ericktijerou.gitstar.ui.util.Pager
import com.ericktijerou.gitstar.ui.util.PagerState

@Composable
fun Home() {
    val navHostController = rememberNavController()
    val sections = listOf(HomeSection.Feed, HomeSection.Profile, HomeSection.Explorer)
    val pagerState = remember { PagerState() }
    GitstarScaffold(
        topBar = { HomeAppBar(Modifier.fillMaxWidth()) },
        bottomBar = {
            HomeBottomNavigation(
                sections = sections,
                pagerState = pagerState
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        HomeViewPager(
            navHostController = navHostController,
            items = sections,
            pagerState = pagerState,
            userInputEnabled = false,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun HomeViewPager(
    navHostController: NavHostController,
    items: List<HomeSection>,
    modifier: Modifier = Modifier,
    userInputEnabled: Boolean = true,
    pagerState: PagerState = remember { PagerState() },
) {
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)
    Pager(
        state = pagerState,
        userInputEnabled = userInputEnabled,
        modifier = modifier
    ) {
        when (items[page]) {
            is HomeSection.Feed -> Feed(
                navHostController = navHostController,
                modifier = Modifier.fillMaxSize()
            )
            is HomeSection.Profile -> Profile(
                modifier = Modifier.fillMaxSize()
            )
            is HomeSection.Explorer -> Profile(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeBottomNavigation(
    sections: List<HomeSection>,
    pagerState: PagerState = remember { PagerState() }
) {
    BottomNavigation(
        backgroundColor = GitstarTheme.colors.iconPrimary,
        contentColor = GitstarTheme.colors.textInteractive
    ) {
        sections.forEachIndexed { index, section ->
            BottomNavigationItem(
                icon = { Icon(imageVector = section.icon, contentDescription = EMPTY) },
                label = { Text(stringResource(section.resourceId)) },
                alwaysShowLabels = false,
                selected = pagerState.currentPage == index,
                onClick = { pagerState.currentPage = index }
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
    object Explorer : HomeSection("explorer", R.string.home_profile, Icons.Outlined.AccountCircle)
}
