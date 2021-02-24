package com.ericktijerou.gitstar.ui.home.feed.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ericktijerou.gitstar.domain.usecase.GetRepoListUseCase
import com.ericktijerou.gitstar.ui.entity.RepoView
import com.ericktijerou.gitstar.ui.entity.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val getRepoListUseCase: GetRepoListUseCase
) : ViewModel() {

    val repoList: Flow<PagingData<RepoView>> by lazy {
        getRepoListUseCase.invoke("stars:>100 sort:stars-desc").map { pagingData ->
            pagingData.map { it.toView() }
        }.cachedIn(viewModelScope)
    }
}