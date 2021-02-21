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