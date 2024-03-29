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
package com.ericktijerou.gitstar.ui.util

import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.ui.entity.RepoView
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

    val repo by lazy {
        RepoView(
            id = "id",
            name = "name",
            description = "description",
            forkCount = "forkCount",
            stargazerCount = "stargazerCount",
            owner = "owner",
            primaryLanguage = "primaryLanguage",
            colorLanguage = "colorLanguage",
            socialImage = "socialImage",
            updatedAt = "updatedAt"
        )
    }
}
