package com.ericktijerou.gitstar.data.entity

import com.ericktijerou.gitstar.data.local.entity.RepoEntity
import com.ericktijerou.gitstar.data.local.entity.UserEntity

fun UserModel.toLocal() = UserEntity(
    id = id,
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

fun RepoModel.toLocal() = RepoEntity(
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