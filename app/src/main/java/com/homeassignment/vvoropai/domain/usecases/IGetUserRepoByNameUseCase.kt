package com.homeassignment.vvoropai.domain.usecases

import com.homeassignment.vvoropai.domain.models.UserRepoModel
import kotlinx.coroutines.flow.Flow

interface IGetUserRepoDetailsUseCase {
    suspend fun execute(repoName: String): Flow<UserRepoModel?>
}
