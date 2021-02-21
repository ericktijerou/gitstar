package com.ericktijerou.gitstar.data.repository.user

import androidx.paging.*
import com.ericktijerou.gitstar.core.DEFAULT_PAGE_SIZE
import com.ericktijerou.gitstar.data.local.UserDataStore
import com.ericktijerou.gitstar.data.local.entity.toDomain
import com.ericktijerou.gitstar.data.remote.UserCloudStore
import com.ericktijerou.gitstar.domain.entity.User
import com.ericktijerou.gitstar.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val local: UserDataStore,
    private val remote: UserCloudStore
) : UserRepository {

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @ExperimentalPagingApi
    override fun getUserList(query: String): Flow<PagingData<User>> {
        val pagingSourceFactory = { local.getUserList() }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = UserRemoteMediator(
                local,
                remote,
                query
            )
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }
}