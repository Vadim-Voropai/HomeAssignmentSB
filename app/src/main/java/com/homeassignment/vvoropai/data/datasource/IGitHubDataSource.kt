package com.homeassignment.vvoropai.data.datasource

import com.homeassignment.vvoropai.data.responses.UserInfoResponse
import com.homeassignment.vvoropai.data.responses.UserRepoResponse
import retrofit2.Response

interface IGitHubDataSource {
    suspend fun getUserById(username: String): Response<UserInfoResponse>
    suspend fun getReposByUsername(username: String): Response<List<UserRepoResponse>>
}
