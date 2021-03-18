/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.gitstar.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.ui.component.GitstarScaffold
import com.ericktijerou.gitstar.ui.main.repo.RepoScreen
import com.ericktijerou.gitstar.ui.main.user.UserScreen
import com.ericktijerou.gitstar.ui.util.Pager
import com.ericktijerou.gitstar.ui.util.PagerState

@Composable
fun MainScreen() {
    val sections = listOf(MainSection.User, MainSection.Repo)
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
            items = sections,
            pagerState = pagerState,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun HomeViewPager(
    items: List<MainSection>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
) {
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)
    Pager(
        state = pagerState,
        modifier = modifier,
        userInputEnabled = false
    ) {
        when (items[page]) {
            is MainSection.User -> UserScreen(
                modifier = Modifier.fillMaxSize()
            )
            is MainSection.Repo -> RepoScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeBottomNavigation(
    sections: List<MainSection>,
    pagerState: PagerState = remember { PagerState() }
) {
    BottomNavigation {
        sections.forEachIndexed { index, section ->
            BottomNavigationItem(
                icon = { Icon(imageVector = section.icon, contentDescription = EMPTY) },
                label = { Text(stringResource(section.resourceId)) },
                selected = pagerState.currentPage == index,
                onClick = { pagerState.currentPage = index }
            )
        }
    }
}

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .height(24.dp)
            )
        },
        modifier = modifier
    )
}

sealed class MainSection(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object User : MainSection("user", R.string.label_user, Icons.Outlined.Home)
    object Repo : MainSection("repo", R.string.label_repo, Icons.Outlined.AccountCircle)
}
