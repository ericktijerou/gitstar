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
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.apollographql.apollo.exception.ApolloHttpException
import com.ericktijerou.gitstar.data.local.RepoDataStore
import com.ericktijerou.gitstar.data.local.entity.RepoEntity
import com.ericktijerou.gitstar.data.remote.RepoCloudStore
import java.io.IOException

@ExperimentalPagingApi
class RepoRemoteMediator(
    private val local: RepoDataStore,
    private val remote: RepoCloudStore,
    private val query: String
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        val cursor = when (val pageKeyData = getKeyPageData(loadType)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as? String
        }
        return try {
            val (pageInfo, userList) = remote.getRepoList(
                cursor,
                state.config.pageSize,
                query
            )
            local.doOperationInTransaction {
                if (loadType == LoadType.REFRESH) local.clearRepositories()
                local.setLastRepositoryCursor(pageInfo.endCursor)
                local.saveRepositoryList(userList)
            }
            MediatorResult.Success(endOfPaginationReached = !pageInfo.hasNextPage)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: ApolloHttpException) {
            MediatorResult.Error(exception)
        }
    }

    private fun getKeyPageData(loadType: LoadType): Any? {
        return when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastRepositoryCursor()
        }
    }
}
