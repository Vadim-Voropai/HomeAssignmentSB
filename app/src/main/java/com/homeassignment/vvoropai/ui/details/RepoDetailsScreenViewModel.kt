package com.homeassignment.vvoropai.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeassignment.vvoropai.domain.models.UserRepoModel
import com.homeassignment.vvoropai.domain.usecases.IGetUserRepoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class RepoDetailsUiState(
    val repoDetails: UserRepoModel? = null,
    val isLoading: Boolean = false
) {
    fun isEmpty() = repoDetails == null
}

@HiltViewModel
class RepoDetailsScreenViewModel @Inject constructor(
    private val getUserRepoDetailsUseCase: IGetUserRepoDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repoName: StateFlow<String?> = savedStateHandle.getStateFlow("repoName", null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<RepoDetailsUiState> = repoName.flatMapLatest { name ->
        if (name == null) {
            flowOf(RepoDetailsUiState())
        } else {
            getUserRepoDetailsUseCase.execute(name).map { details ->
                RepoDetailsUiState(repoDetails = details)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RepoDetailsUiState(isLoading = true)
    )
}
