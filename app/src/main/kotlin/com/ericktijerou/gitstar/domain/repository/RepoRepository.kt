package com.ericktijerou.gitstar.domain.repository

import androidx.paging.PagingData
import com.ericktijerou.gitstar.domain.entity.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun getRepoList(query: String): Flow<PagingData<Repo>>
}