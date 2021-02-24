package com.ericktijerou.gitstar.ui.home.feed.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.ui.entity.UserView
import com.ericktijerou.gitstar.ui.util.hiltViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun UserScreen(
    navHostController: NavHostController,
    modifier: Modifier
) {
    val viewModel: UserViewModel by hiltViewModel()
    val lazyMovieItems = viewModel.userList.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier) {
        items(lazyMovieItems) { user ->
            UserItem(user = user!!)
        }
        lazyMovieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    //item { LoadingItem() }
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
fun UserItem(user: UserView) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserName(
            user.name,
            modifier = Modifier.weight(1f)
        )
        UserImage(
            user.avatarUrl,
            modifier = Modifier.padding(start = 16.dp).preferredSize(90.dp)
        )
    }
}

@Composable
fun UserImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    CoilImage(
        data = imageUrl,
        contentDescription = EMPTY,
        modifier = modifier,
        fadeIn = true,
        contentScale = ContentScale.Crop,
        loading = {
            Image(painterResource(id = R.drawable.ic_clock), alpha = 0.45f, contentDescription = EMPTY)
        },
        error = {
            Image(painterResource(id = R.drawable.ic_fork), alpha = 0.45f, contentDescription = EMPTY)
        }
    )
}

@Composable
fun UserName(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title,
        maxLines = 2,
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis
    )
}