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
package com.ericktijerou.gitstar.di

import com.apollographql.apollo.ApolloClient
import com.ericktijerou.gitstar.data.local.RepoDataStore
import com.ericktijerou.gitstar.data.local.UserDataStore
import com.ericktijerou.gitstar.data.local.dao.RepoDao
import com.ericktijerou.gitstar.data.local.dao.UserDao
import com.ericktijerou.gitstar.data.local.system.GitstarDatabase
import com.ericktijerou.gitstar.data.local.system.PagingManager
import com.ericktijerou.gitstar.data.remote.RepoCloudStore
import com.ericktijerou.gitstar.data.remote.UserCloudStore
import com.ericktijerou.gitstar.data.repository.repo.RepoRepositoryImpl
import com.ericktijerou.gitstar.data.repository.user.UserRepositoryImpl
import com.ericktijerou.gitstar.domain.repository.RepoRepository
import com.ericktijerou.gitstar.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideUserDataStore(
        database: GitstarDatabase,
        userDao: UserDao,
        pagingManager: PagingManager
    ): UserDataStore {
        return UserDataStore(
            database,
            userDao,
            pagingManager
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepositoryDataStore(
        database: GitstarDatabase,
        repositoryDao: RepoDao,
        pagingManager: PagingManager
    ): RepoDataStore {
        return RepoDataStore(
            database,
            repositoryDao,
            pagingManager
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideUserCloudStore(apolloClient: ApolloClient): UserCloudStore {
        return UserCloudStore(apolloClient)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepoCloudStore(apolloClient: ApolloClient): RepoCloudStore {
        return RepoCloudStore(apolloClient)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideUserRepository(
        local: UserDataStore,
        remote: UserCloudStore
    ): UserRepository {
        return UserRepositoryImpl(
            local,
            remote
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepoRepository(
        local: RepoDataStore,
        remote: RepoCloudStore
    ): RepoRepository {
        return RepoRepositoryImpl(
            local,
            remote
        )
    }
}
