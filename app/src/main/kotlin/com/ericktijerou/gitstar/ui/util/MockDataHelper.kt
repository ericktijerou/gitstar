package com.ericktijerou.gitstar.ui.util

import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.entity.UserView

object MockDataHelper {
    val user by lazy {
        UserView(
            name = "name",
            username = "username",
            avatarUrl = "",
            bio = "bio",
            hasCompany = false,
            info = "info",
            infoIcon = R.drawable.ic_clock,
            url = "url",
            followersCount = "12",
            stargazerCount = "12",
            primaryLanguage = "Kotlin",
            colorLanguage = " #000000"
        )
    }
}