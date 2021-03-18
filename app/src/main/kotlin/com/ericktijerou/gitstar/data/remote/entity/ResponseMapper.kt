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
package com.ericktijerou.gitstar.data.remote.entity

import com.ericktijerou.SearchRepositoriesQuery
import com.ericktijerou.SearchUsersQuery
import com.ericktijerou.gitstar.core.asString
import com.ericktijerou.gitstar.data.entity.RepoModel
import com.ericktijerou.gitstar.data.entity.UserModel

fun SearchRepositoriesQuery.AsRepository.toData(): RepoModel {
    return RepoModel(
        id = id,
        name = name,
        description = description.orEmpty(),
        forkCount = forks.totalCount,
        stargazerCount = stargazers.totalCount,
        owner = owner.login,
        primaryLanguage = primaryLanguage?.name.orEmpty(),
        colorLanguage = primaryLanguage?.color.orEmpty(),
        updatedAt = updatedAt.asString(),
        socialImage = openGraphImageUrl.asString()
    )
}

fun SearchUsersQuery.AsUser.toData() = UserModel(
    id = id,
    name = name.orEmpty(),
    username = login,
    avatarUrl = avatarUrl.asString(),
    bio = bio.orEmpty().trim(),
    company = company.orEmpty(),
    createdAt = createdAt.asString(),
    url = url.asString(),
    followersCount = followers.totalCount
)

fun SearchUsersQuery.PrimaryLanguage.toData() = LanguageResponse(
    name = name,
    color = color.orEmpty()
)
