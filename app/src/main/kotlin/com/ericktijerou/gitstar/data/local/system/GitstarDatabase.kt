package com.ericktijerou.gitstar.data.local.system

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ericktijerou.gitstar.data.local.dao.RepoDao
import com.ericktijerou.gitstar.data.local.dao.UserDao
import com.ericktijerou.gitstar.data.local.entity.RepoEntity
import com.ericktijerou.gitstar.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, RepoEntity::class], version = 1)
abstract class GitstarDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao

    companion object {
        private const val DB_NAME = "gitstar_database"

        fun newInstance(context: Context): GitstarDatabase {
            return Room.databaseBuilder(
                context, GitstarDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}