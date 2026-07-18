package com.homeassignment.vvoropai.data.datasource

import com.homeassignment.vvoropai.data.responses.UserInfoResponse
import com.homeassignment.vvoropai.data.responses.UserRepoResponse
import com.homeassignment.vvoropai.network.ApiService
import retrofit2.Response

class GitHubDataSource(private val api: ApiService) : IGitHubDataSource {
    override suspend fun getUserById(username: String): Response<UserInfoResponse> {
        return api.getUserById(username)
    }

    override suspend fun getReposByUsername(username: String): Response<List<UserRepoResponse>> {
        return api.getReposByUsername(username)
    }
}
