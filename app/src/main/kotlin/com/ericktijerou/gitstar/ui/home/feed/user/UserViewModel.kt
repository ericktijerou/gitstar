package com.ericktijerou.gitstar.ui.home.feed.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ericktijerou.gitstar.domain.usecase.GetUserListUseCase
import com.ericktijerou.gitstar.ui.entity.UserView
import com.ericktijerou.gitstar.ui.entity.toView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    val userList: Flow<PagingData<UserView>> by lazy {
        getUserListUseCase.invoke("followers:>100 sort:followers-desc").map { pagingData ->
            pagingData.map { it.toView() }
        }.cachedIn(viewModelScope)
    }
}