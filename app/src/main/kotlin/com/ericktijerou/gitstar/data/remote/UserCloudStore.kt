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