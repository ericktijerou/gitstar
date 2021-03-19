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
package com.ericktijerou.gitstar.ui.main.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.component.Indicator
import com.ericktijerou.gitstar.ui.component.LanguageIndicator
import com.ericktijerou.gitstar.ui.component.TextIndicator
import com.ericktijerou.gitstar.ui.entity.UserView
import com.ericktijerou.gitstar.ui.theme.GitstarTheme
import com.ericktijerou.gitstar.ui.util.MockDataHelper
import com.ericktijerou.gitstar.ui.util.ThemedPreview
import com.ericktijerou.gitstar.ui.util.hiltViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun UserListScreen(modifier: Modifier = Modifier) {
    val viewModel: UserViewModel by hiltViewModel()
    val lazyMovieItems = viewModel.userList.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(lazyMovieItems) { user ->
            UserItem(
                user = user!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        lazyMovieItems.apply {
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
fun UserItem(user: UserView, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(0)) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            UserHeader(user = user, modifier = Modifier.padding(horizontal = 16.dp))
            if (user.bio.isNotEmpty()) {
                Text(
                    text = user.bio,
                    maxLines = 3,
                    style = GitstarTheme.typography.body1,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
            UserFooter(
                user = user, modifier = Modifier
                    .height(40.dp)
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun UserHeader(user: UserView, modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        CoilImage(
            data = user.avatarUrl,
            contentDescription = stringResource(id = R.string.label_user),
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        UserHeaderInfo(
            user = user,
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .padding(horizontal = 8.dp)
        )
        Text(
            text = "#1",
            maxLines = 1,
            style = GitstarTheme.typography.body2
        )
    }
}

@Composable
fun UserHeaderInfo(user: UserView, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = user.name,
            maxLines = 1,
            style = GitstarTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(1f),
            text = user.username,
            maxLines = 1,
            style = GitstarTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            color = GitstarTheme.customColors.textSecondaryColor
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = user.infoIcon),
                contentDescription = "Info icon",
                tint = GitstarTheme.customColors.textSecondaryColor
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = user.info,
                maxLines = 1,
                style = GitstarTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                color = GitstarTheme.customColors.textSecondaryColor
            )
        }
    }
}

@Composable
fun UserFooter(user: UserView, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        val color =
            if (user.colorLanguage.isNotEmpty()) Color(android.graphics.Color.parseColor(user.colorLanguage)) else GitstarTheme.customColors.textSecondaryColor
        val indicators = listOf(
            Indicator(icon = R.drawable.ic_people, label = user.followersCount),
            Indicator(icon = R.drawable.ic_star, label = user.stargazerCount),
        )
        LanguageIndicator(
            color = color,
            label = user.primaryLanguage,
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


@Preview("User item screen")
@Composable
fun PreviewUserItem() {
    ThemedPreview {
        UserItem(MockDataHelper.user)
    }
}

@Preview("User item screen dark")
@Composable
fun PreviewUserItemDark() {
    ThemedPreview(darkTheme = true) {
        UserItem(MockDataHelper.user)
    }
}

