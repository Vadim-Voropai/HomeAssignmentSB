package com.homeassignment.vvoropai.domain.models

data class UserData(
    val userId: String,
    val userInfo: UserInfoModel,
    val repos: List<UserRepoModel>
)