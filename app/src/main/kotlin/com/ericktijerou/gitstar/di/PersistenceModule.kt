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