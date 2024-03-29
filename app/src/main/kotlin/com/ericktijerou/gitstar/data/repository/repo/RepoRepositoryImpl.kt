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
package com.ericktijerou.gitstar.data.repository.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
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
