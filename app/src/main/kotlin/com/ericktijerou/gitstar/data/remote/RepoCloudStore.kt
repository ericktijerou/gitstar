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