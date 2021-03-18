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
import com.ericktijerou.SearchTotalStarsQuery
import com.ericktijerou.SearchUsersQuery
import com.ericktijerou.gitstar.core.exception.NotFoundException
import com.ericktijerou.gitstar.core.orZero
import com.ericktijerou.gitstar.data.entity.PageInfoModel
import com.ericktijerou.gitstar.data.entity.UserModel
import com.ericktijerou.gitstar.data.remote.entity.LanguageResponse
import com.ericktijerou.gitstar.data.remote.entity.toData
import com.ericktijerou.gitstar.data.remote.util.suspendQuery
import javax.inject.Inject

class UserCloudStore @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getUserList(
        cursor: String?,
        pageSize: Int,
        query: String
    ): Pair<PageInfoModel, List<UserModel>> {
        val searchResponse = searchUsers(cursor, pageSize, query)
        val userResponse = getUserResponse(searchResponse)
        return PageInfoModel(
            searchResponse.pageInfo.endCursor,
            searchResponse.pageInfo.hasNextPage
        ) to userResponse
    }

    private suspend fun searchUsers(
        cursor: String?,
        pageSize: Int,
        query: String
    ): SearchUsersQuery.Search {
        return apolloClient.suspendQuery(
            SearchUsersQuery(
                pageSize,
                Input.fromNullable(cursor),
                query
            )
        ).data?.search ?: throw NotFoundException()
    }

    private fun getUserResponse(searchResponse: SearchUsersQuery.Search): List<UserModel> {
        return searchResponse.nodes?.map { node ->
            var stargazerCount = 0
            val languageList = mutableListOf<LanguageResponse>()
            node?.asUser?.repositories?.nodes?.forEach { repo ->
                repo?.primaryLanguage?.toData()?.let { languageModel ->
                    languageList.add(languageModel)
                }
                stargazerCount += repo?.stargazerCount.orZero()
            }
            val language =
                languageList.groupBy { language -> language.name }.values.maxByOrNull { it.size }
                    ?.first()
            node?.asUser?.toData()?.apply {
                this.stargazerCount = stargazerCount
                this.languagePrimary = language?.name.orEmpty()
                this.languageColor = language?.color.orEmpty()
            } ?: throw NotFoundException()
        } ?: throw NotFoundException()
    }

    private suspend fun searchRepositoriesByUser(
        cursor: String?,
        pageSize: Int,
        query: String
    ): SearchTotalStarsQuery.Search {
        return apolloClient.suspendQuery(
            SearchTotalStarsQuery(
                pageSize,
                Input.fromNullable(cursor),
                query
            )
        ).data?.search ?: throw NotFoundException()
    }
}
