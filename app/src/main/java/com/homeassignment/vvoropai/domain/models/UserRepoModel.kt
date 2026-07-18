package com.homeassignment.vvoropai.domain.models

data class UserRepoModel(
    val name: String,
    val description: String,
    val updatedAt: String,
    val starCount: Int,
    val forksCount: Int,
)