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

import android.content.Context
import com.ericktijerou.gitstar.data.local.system.GitstarDatabase
import com.ericktijerou.gitstar.data.local.system.PagingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PersistenceModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        GitstarDatabase.newInstance(context)

    @Singleton
    @Provides
    fun providePagingManager(@ApplicationContext context: Context) = PagingManager(context)

    @Singleton
    @Provides
    fun provideUserDao(database: GitstarDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideRepoDao(database: GitstarDatabase) = database.repoDao()
}
