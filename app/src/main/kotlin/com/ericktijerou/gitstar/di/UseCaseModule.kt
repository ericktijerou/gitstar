package com.ericktijerou.gitstar.di

import com.ericktijerou.gitstar.domain.repository.RepoRepository
import com.ericktijerou.gitstar.domain.repository.UserRepository
import com.ericktijerou.gitstar.domain.usecase.GetRepoListUseCase
import com.ericktijerou.gitstar.domain.usecase.GetUserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserListUseCase {
        return GetUserListUseCase(userRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetRepoUseCase(repoRepository: RepoRepository): GetRepoListUseCase {
        return GetRepoListUseCase(repoRepository)
    }
}