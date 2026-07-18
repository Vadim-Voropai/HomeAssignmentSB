package com.homeassignment.vvoropai.data.responses

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
)
