package com.homeassignment.vvoropai.domain.usecases

import com.homeassignment.vvoropai.data.NetworkResult
import com.homeassignment.vvoropai.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface IGetUserByIdUseCase {
    suspend fun execute(username: String): Flow<NetworkResult<UserData>>
}
