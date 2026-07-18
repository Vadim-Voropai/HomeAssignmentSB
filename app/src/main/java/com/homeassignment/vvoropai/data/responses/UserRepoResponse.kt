package com.homeassignment.vvoropai.data.responses

import com.google.gson.annotations.SerializedName

data class UserRepoResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("stargazers_count")
    val starCount: Int?,
    @SerializedName("forks_count")
    val forksCount: Int?,
)
