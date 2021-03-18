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
