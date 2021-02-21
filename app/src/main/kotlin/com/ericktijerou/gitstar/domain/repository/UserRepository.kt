package com.ericktijerou.gitstar.domain.repository

import androidx.paging.PagingData
import com.ericktijerou.gitstar.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserList(query: String): Flow<PagingData<User>>
}