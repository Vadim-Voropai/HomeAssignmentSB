package com.homeassignment.vvoropai.domain.usecases

import com.homeassignment.vvoropai.domain.IGitHubRepository
import com.homeassignment.vvoropai.domain.models.UserRepoModel
import kotlinx.coroutines.flow.Flow

class GetUserRepoDetailsUseCase(private val gitHubRepository: IGitHubRepository) : IGetUserRepoDetailsUseCase {
    override suspend fun execute(repoName: String): Flow<UserRepoModel?> =
        gitHubRepository.getUserRepoDetails(repoName = repoName)
}
