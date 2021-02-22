package com.ericktijerou.gitstar.ui.home.feed.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ericktijerou.gitstar.R

@Composable
fun UserList(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.home_user_list),
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
    )
}