package com.ericktijerou.gitstar.ui

import androidx.annotation.StringRes
import com.ericktijerou.gitstar.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home_profile)
}