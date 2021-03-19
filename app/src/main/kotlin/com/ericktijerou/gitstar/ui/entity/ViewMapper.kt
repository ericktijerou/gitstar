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
package com.ericktijerou.gitstar.ui.entity

import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.domain.entity.Repo
import com.ericktijerou.gitstar.domain.entity.User
import com.ericktijerou.gitstar.ui.util.StringProvider
import com.ericktijerou.gitstar.ui.util.toJoinedDate
import com.ericktijerou.gitstar.ui.util.toRelativeTime
import java.util.Locale

fun User.toView(stringProvider: StringProvider): UserView {
    val hasCompany = company.isNotEmpty()
    val info = if (hasCompany) company else createdAt.toJoinedDate()
    return UserView(
        name = name,
        username = username,
        avatarUrl = avatarUrl,
        bio = bio,
        hasCompany = hasCompany,
        info = stringProvider.getUserInfoFormatted(hasCompany, info),
        infoIcon = if (hasCompany) R.drawable.ic_org else R.drawable.ic_clock,
        url = url,
        followersCount = followersCount.toString(),
        stargazerCount = stargazerCount.toString(),
        primaryLanguage = languagePrimary,
        colorLanguage = languageColor
    )
}

fun Repo.toView() = RepoView(
    id = id,
    name = name,
    description = description,
    forkCount = forkCount.toString(),
    stargazerCount = stargazerCount.toString(),
    owner = owner,
    primaryLanguage = primaryLanguage,
    colorLanguage = colorLanguage,
    socialImage = if (!socialImage.contains("avatars")) socialImage else EMPTY,
    updatedAt = updatedAt.toRelativeTime().toLowerCase(Locale.getDefault())
)
