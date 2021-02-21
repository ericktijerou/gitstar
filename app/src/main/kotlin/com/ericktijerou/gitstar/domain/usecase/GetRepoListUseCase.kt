package com.ericktijerou.gitstar.domain.usecase

import com.ericktijerou.gitstar.domain.repository.RepoRepository
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(private val repository: RepoRepository) {
    operator fun invoke(query: String) = repository.getRepoList(query)
}