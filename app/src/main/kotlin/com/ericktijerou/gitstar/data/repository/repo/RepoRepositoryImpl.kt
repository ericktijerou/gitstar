package com.ericktijerou.gitstar.data.repository.repo

import androidx.paging.*
import com.ericktijerou.gitstar.core.DEFAULT_PAGE_SIZE
import com.ericktijerou.gitstar.data.local.RepoDataStore
import com.ericktijerou.gitstar.data.local.entity.toDomain
import com.ericktijerou.gitstar.data.remote.RepoCloudStore
import com.ericktijerou.gitstar.domain.entity.Repo
import com.ericktijerou.gitstar.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val local: RepoDataStore,
    private val remote: RepoCloudStore
) : RepoRepository {

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @ExperimentalPagingApi
    override fun getRepoList(query: String): Flow<PagingData<Repo>> {
        val pagingSourceFactory = { local.getRepositoryList() }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = RepoRemoteMediator(
                local,
                remote,
                query
            )
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }
}