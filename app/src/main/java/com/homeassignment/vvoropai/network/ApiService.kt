package com.homeassignment.vvoropai.network

import com.homeassignment.vvoropai.data.responses.UserInfoResponse
import com.homeassignment.vvoropai.data.responses.UserRepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{username}")
    suspend fun getUserById(@Path("username") username: String): Response<UserInfoResponse>

    @GET("users/{username}/repos")
    suspend fun getReposByUsername(@Path("username") username: String): Response<List<UserRepoResponse>>
}
