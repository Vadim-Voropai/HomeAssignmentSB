package com.homeassignment.vvoropai.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeassignment.vvoropai.data.NetworkResult
import com.homeassignment.vvoropai.domain.models.UserInfoModel
import com.homeassignment.vvoropai.domain.models.UserRepoModel
import com.homeassignment.vvoropai.domain.usecases.IGetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val userInfo: UserInfoModel? = null,
    val repoList: List<UserRepoModel> = listOf(),
) {
    fun isEmpty() = userInfo == null
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getUserByIdUseCase: IGetUserByIdUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = searchQuery
        .debounce(500.milliseconds)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(HomeUiState())
            } else {
                getUserByIdUseCase.execute(query).map { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            HomeUiState(
                                userInfo = result.data?.userInfo,
                                repoList = result.data?.repos ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is NetworkResult.Error -> {
                            HomeUiState(
                                isError = true,
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }

                        is NetworkResult.Loading -> {
                            HomeUiState(isLoading = true)
                        }
                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(),
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

}
