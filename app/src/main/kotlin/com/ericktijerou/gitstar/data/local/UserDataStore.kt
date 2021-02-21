package com.ericktijerou.gitstar.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ericktijerou.gitstar.data.entity.UserModel
import com.ericktijerou.gitstar.data.entity.toLocal
import com.ericktijerou.gitstar.data.local.dao.UserDao
import com.ericktijerou.gitstar.data.local.entity.UserEntity
import com.ericktijerou.gitstar.data.local.system.GitstarDatabase
import com.ericktijerou.gitstar.data.local.system.PagingManager
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val database: GitstarDatabase,
    private val userDao: UserDao,
    private val pagingManager: PagingManager
) {
    fun getUserList(): PagingSource<Int, UserEntity> {
        return userDao.getAll()
    }

    suspend fun saveUserList(list: List<UserModel>) {
        userDao.insertUsers(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun doOperationInTransaction(method: suspend () -> Unit) {
        database.withTransaction {
            method()
        }
    }

    suspend fun clearUsers() {
        userDao.clearAll()
    }

    fun getLastUserCursor(): String? = pagingManager.lastUserCursor

    fun setLastUserCursor(value: String?) {
        pagingManager.lastUserCursor = value
    }
}