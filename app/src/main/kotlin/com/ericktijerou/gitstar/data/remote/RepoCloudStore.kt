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
package com.ericktijerou.gitstar.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.ericktijerou.SearchRepositoriesQuery
import com.ericktijerou.gitstar.core.exception.NotFoundException
import com.ericktijerou.gitstar.data.entity.PageInfoModel
import com.ericktijerou.gitstar.data.entity.RepoModel
import com.ericktijerou.gitstar.data.remote.entity.toData
import com.ericktijerou.gitstar.data.remote.util.suspendQuery
import javax.inject.Inject

class RepoCloudStore @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getRepoList(
        cursor: String?,
        pageSize: Int,
        query: String
    ): Pair<PageInfoModel, List<RepoModel>> {
        return apolloClient.suspendQuery(
            SearchRepositoriesQuery(pageSize, Input.fromNullable(cursor), query)
        ).data?.search?.run {
            val results = nodes?.map {
                it?.asRepository?.toData()
                    ?: throw NotFoundException()
            } ?: throw NotFoundException()
            PageInfoModel(pageInfo.endCursor, pageInfo.hasNextPage) to results
        } ?: throw NotFoundException()
    }
}
