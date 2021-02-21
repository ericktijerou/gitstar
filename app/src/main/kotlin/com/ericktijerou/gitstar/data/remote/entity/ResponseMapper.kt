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

