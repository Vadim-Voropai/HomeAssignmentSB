package com.homeassignment.vvoropai.domain

import com.homeassignment.vvoropai.data.NetworkResult
import com.homeassignment.vvoropai.domain.models.UserData
import com.homeassignment.vvoropai.domain.models.UserInfoModel
import com.homeassignment.vvoropai.domain.models.UserRepoModel
import kotlinx.coroutines.flow.Flow

interface IGitHubRepository {
    suspend fun getUserDataById(username: String): Flow<NetworkResult<UserData>>
    fun getUserRepoDetails(repoName: String): Flow<UserRepoModel?>
}
