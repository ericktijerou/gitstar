package com.ericktijerou.gitstar.data.local.entity

import com.ericktijerou.gitstar.domain.entity.Repo
import com.ericktijerou.gitstar.domain.entity.User

fun RepoEntity.toDomain() = Repo(
    id = id,
    name = name,
    description = description,
    forkCount = forkCount,
    stargazerCount = stargazerCount,
    owner = owner,
    primaryLanguage = primaryLanguage,
    colorLanguage = colorLanguage,
    socialImage = socialImage,
    updatedAt = updatedAt
)

fun UserEntity.toDomain() = User(
    name = name,
    username = username,
    avatarUrl = avatarUrl,
    bio = bio,
    company = company,
    createdAt = createdAt,
    url = url,
    followersCount = followersCount,
    stargazerCount = stargazerCount,
    languagePrimary = languagePrimary,
    languageColor = languageColor
)