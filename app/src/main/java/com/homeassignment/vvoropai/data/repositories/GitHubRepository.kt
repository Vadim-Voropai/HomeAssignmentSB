package com.homeassignment.vvoropai.data.repositories

import com.homeassignment.vvoropai.data.NetworkResult
import com.homeassignment.vvoropai.data.datasource.IGitHubDataSource
import com.homeassignment.vvoropai.data.responses.UserInfoResponse
import com.homeassignment.vvoropai.data.responses.UserRepoResponse
import com.homeassignment.vvoropai.domain.IGitHubRepository
import com.homeassignment.vvoropai.domain.models.UserData
import com.homeassignment.vvoropai.domain.models.UserInfoModel
import com.homeassignment.vvoropai.domain.models.UserRepoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class GitHubRepository(
    private val gitHubDataSource: IGitHubDataSource,
) : IGitHubRepository {

    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val userData: StateFlow<UserData?> = _userData.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserRepoDetails(repoName: String): Flow<UserRepoModel?> =
        userData.mapLatest { userData ->
            userData?.repos?.find { it.name == repoName }
        }

    override suspend fun getUserDataById(username: String): Flow<NetworkResult<UserData>> =
        combine(
            getUserByIdInternal(username),
            getReposByUsername(username)
        ) { userResult, reposResult ->
            when {
                userResult is NetworkResult.Error -> NetworkResult.Error(userResult.message)
                reposResult is NetworkResult.Error -> NetworkResult.Error(reposResult.message)
                userResult is NetworkResult.Loading || reposResult is NetworkResult.Loading -> NetworkResult.Loading()
                userResult is NetworkResult.Success && reposResult is NetworkResult.Success -> {
                    val data = UserData(
                        userId = username,
                        userInfo = userResult.data!!,
                        repos = reposResult.data ?: emptyList()
                    )
                    _userData.value = data
                    NetworkResult.Success(data)
                }
                else -> NetworkResult.Error("Unknown error")
            }
        }

    private suspend fun getUserByIdInternal(username: String): Flow<NetworkResult<UserInfoModel>> =
        flow {
            emit(NetworkResult.Loading())
            val response = gitHubDataSource.getUserById(username)
            if (response.isSuccessful) {
                emit(NetworkResult.Success(response.body()?.toDomainModel()))
            } else {
                emit(NetworkResult.Error(response.errorBody()?.string() ?: "Unknown error"))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }

    private suspend fun getReposByUsername(username: String): Flow<NetworkResult<List<UserRepoModel>>> =
        flow {
            emit(NetworkResult.Loading())
            val response = gitHubDataSource.getReposByUsername(username)
            if (response.isSuccessful) {
                val repos = response.body()?.map { it.toDomainModel() } ?: emptyList()
                emit(NetworkResult.Success(repos))
            } else {
                emit(NetworkResult.Error(response.errorBody()?.string() ?: "Unknown error"))
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }

    //-------------------- Extensions "toDomainModel"--------------------

    private fun UserInfoResponse.toDomainModel(): UserInfoModel =
        UserInfoModel(
            name = this.name ?: "",
            avatarUrl = this.avatarUrl ?: "",
        )

    private fun UserRepoResponse.toDomainModel(): UserRepoModel =
        UserRepoModel(
            name = this.name ?: "",
            description = this.description ?: "",
            updatedAt = this.updatedAt ?: "",
            starCount = this.starCount ?: 0,
            forksCount = this.forksCount ?: 0
        )

    //----------------------------------------
}
