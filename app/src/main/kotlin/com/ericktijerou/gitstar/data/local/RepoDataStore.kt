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
package com.ericktijerou.gitstar.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ericktijerou.gitstar.data.entity.RepoModel
import com.ericktijerou.gitstar.data.entity.toLocal
import com.ericktijerou.gitstar.data.local.dao.RepoDao
import com.ericktijerou.gitstar.data.local.entity.RepoEntity
import com.ericktijerou.gitstar.data.local.system.GitstarDatabase
import com.ericktijerou.gitstar.data.local.system.PagingManager
import javax.inject.Inject

class RepoDataStore @Inject constructor(
    private val database: GitstarDatabase,
    private val repositoryDao: RepoDao,
    private val preferences: PagingManager
) {
    fun getRepositoryList(): PagingSource<Int, RepoEntity> {
        return repositoryDao.getAll()
    }

    suspend fun saveRepositoryList(list: List<RepoModel>) {
        repositoryDao.insertRepositories(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun doOperationInTransaction(method: suspend () -> Unit) {
        database.withTransaction {
            method()
        }
    }

    suspend fun clearRepositories() {
        repositoryDao.clearAll()
    }

    fun getLastRepositoryCursor(): String? = preferences.lastRepositoryCursor

    fun setLastRepositoryCursor(value: String?) {
        preferences.lastRepositoryCursor = value
    }
}
