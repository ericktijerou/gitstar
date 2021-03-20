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
package com.ericktijerou.gitstar.ui.main.repo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.component.Indicator
import com.ericktijerou.gitstar.ui.component.LanguageIndicator
import com.ericktijerou.gitstar.ui.component.TextIndicator
import com.ericktijerou.gitstar.ui.entity.RepoView
import com.ericktijerou.gitstar.ui.theme.GitstarTheme
import com.ericktijerou.gitstar.ui.util.MockDataHelper
import com.ericktijerou.gitstar.ui.util.ThemedPreview
import com.ericktijerou.gitstar.ui.util.hiltViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun RepoListScreen(modifier: Modifier = Modifier) {
    val viewModel: RepoViewModel by hiltViewModel()
    val lazyRepoItems = viewModel.repoList.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier) {
        itemsIndexed(lazyRepoItems) { index, repo ->
            repo?.let {
                RepoItem(
                    repo = repo,
                    position = index + 1,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        lazyRepoItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    // item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    // item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    /*val e = lazyMovieItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }*/
                }
                loadState.append is LoadState.Error -> {
                    /*val e = lazyMovieItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }*/
                }
            }
        }
    }
}

@Composable
fun RepoItem(repo: RepoView, position: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(0)) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            RepoHeader(
                repo = repo,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                position = position
            )
            if (repo.description.isNotEmpty()) {
                Text(
                    text = repo.description,
                    maxLines = 3,
                    style = GitstarTheme.typography.body1,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
            CoilImage(
                data = repo.socialImage,
                contentDescription = stringResource(R.string.label_social_image),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            RepoFooter(
                repo = repo, modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun RepoFooter(repo: RepoView, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        val color =
            if (repo.colorLanguage.isNotEmpty()) Color(android.graphics.Color.parseColor(repo.colorLanguage)) else GitstarTheme.customColors.textSecondaryColor
        val indicators = listOf(
            Indicator(icon = R.drawable.ic_star, label = repo.stargazerCount),
            Indicator(icon = R.drawable.ic_fork, label = repo.forkCount),
        )
        LanguageIndicator(
            color = color,
            label = repo.primaryLanguage,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .fillMaxSize(),
            dotSize = 14.dp,
            tint = GitstarTheme.customColors.textSecondaryColor
        )
        indicators.forEach {
            TextIndicator(
                painter = painterResource(id = it.icon),
                label = it.label,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                tint = GitstarTheme.customColors.textSecondaryColor
            )
        }
        TextIndicator(
            painter = painterResource(id = R.drawable.ic_share),
            label = stringResource(id = R.string.label_share),
            tint = GitstarTheme.customColors.textSecondaryColor,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clickable {

                }
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun RepoHeader(repo: RepoView, position: Int, modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.label_repo_title, repo.owner, repo.name),
                maxLines = 1,
                style = GitstarTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(id = R.string.label_updated, repo.updatedAt),
                maxLines = 1,
                style = GitstarTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                color = GitstarTheme.customColors.textSecondaryColor,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            text = "#$position",
            maxLines = 1,
            style = GitstarTheme.typography.body2
        )
    }
}

@Preview("Repo item screen")
@Composable
fun PreviewRepoItem() {
    ThemedPreview {
        RepoItem(MockDataHelper.repo, 1)
    }
}

@Preview("Repo item screen dark")
@Composable
fun PreviewRepoItemDark() {
    ThemedPreview(darkTheme = true) {
        RepoItem(MockDataHelper.repo, 1)
    }
}