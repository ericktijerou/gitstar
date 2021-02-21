package com.ericktijerou.gitstar.domain.usecase

import com.ericktijerou.gitstar.domain.repository.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(query: String) = repository.getUserList(query)
}