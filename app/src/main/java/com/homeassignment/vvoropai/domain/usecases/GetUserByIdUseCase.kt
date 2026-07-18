package com.homeassignment.vvoropai.domain.usecases

import com.homeassignment.vvoropai.data.NetworkResult
import com.homeassignment.vvoropai.domain.IGitHubRepository
import com.homeassignment.vvoropai.domain.models.UserData
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(private val gitHubRepository: IGitHubRepository) : IGetUserByIdUseCase {
    override suspend fun execute(username: String): Flow<NetworkResult<UserData>> =
        gitHubRepository.getUserDataById(username)
}
