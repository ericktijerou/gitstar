package com.ericktijerou.gitstar.data.entity

data class RepoModel(
    val id: String,
    val name: String,
    val description: String,
    val forkCount: Int,
    val stargazerCount: Int,
    val owner: String,
    val primaryLanguage: String,
    val colorLanguage: String,
    val socialImage: String,
    val updatedAt: String
)
