package com.ericktijerou.gitstar.ui.entity

import com.ericktijerou.gitstar.R
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.domain.entity.Repo
import com.ericktijerou.gitstar.domain.entity.User
import com.ericktijerou.gitstar.ui.util.toJoinedDate
import com.ericktijerou.gitstar.ui.util.toRelativeTime
import java.util.*

fun User.toView(): UserView {
    val hasCompany = company.isNotEmpty()
    return UserView(
        name = name,
        username = username,
        avatarUrl = avatarUrl,
        bio = bio,
        hasCompany = hasCompany,
        info = if (hasCompany) company else createdAt.toJoinedDate(),
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