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
package com.ericktijerou.gitstar.ui.home.feed

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.home.feed.repository.RepoScreen
import com.ericktijerou.gitstar.ui.home.feed.user.UserScreen
import com.ericktijerou.gitstar.ui.theme.GitstarTheme
import com.ericktijerou.gitstar.ui.util.Pager
import com.ericktijerou.gitstar.ui.util.PagerState

@Composable
fun Feed(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val pages = listOf(FeedTabs.User, FeedTabs.Repository)
    val pagerState = remember { PagerState() }
    Column(modifier = modifier) {
        FeedTabBar(
            categories = pages,
            pagerState = pagerState
        )
        FeedViewPager(
            navHostController = navHostController,
            items = pages,
            pagerState = pagerState,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FeedViewPager(
    navHostController: NavHostController,
    items: List<FeedTabs>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() },
) {
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)
    Pager(
        state = pagerState,
        modifier = modifier
    ) {
        when (items[page]) {
            is FeedTabs.User -> UserScreen(
                navHostController = navHostController,
                modifier = modifier
            )
            is FeedTabs.Repository -> RepoScreen(
                navHostController = navHostController,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun FeedTabBar(
    categories: List<FeedTabs>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = remember { PagerState() }
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        backgroundColor = GitstarTheme.colors.iconPrimary
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    pagerState.currentPage = index
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
