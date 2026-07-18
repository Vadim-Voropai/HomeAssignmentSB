package com.homeassignment.vvoropai.ui.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.homeassignment.vvoropai.R
import com.homeassignment.vvoropai.ui.components.EmptyView
import com.homeassignment.vvoropai.ui.components.LottieProgressBar
import com.homeassignment.vvoropai.ui.home.HomeUiState
import com.homeassignment.vvoropai.ui.theme.dimens
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeView(
    uiState: HomeUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNavigationRequested: (repoName: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stickyHeader {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )
                },
                label = { Text("Search") }
            )
        }

        if (uiState.isLoading) {
            item {
                LottieProgressBar()
            }
        } else {
            if (uiState.isEmpty()) {
                item {
                    EmptyView(message = stringResource(R.string.user_not_found_message))
                }
            } else {
                uiState.userInfo?.let {
                    item {
                        UserThumbnail(thumbnailUrl = it.avatarUrl)
                    }
                }
                repositoryListItems(repos = uiState.repoList) {
                    onNavigationRequested(it)
                }
            }
        }
    }
}

@Composable
private fun UserThumbnail(thumbnailUrl: String) {
    GlideImage(
        imageModel = { thumbnailUrl },
        modifier = Modifier
            .height(MaterialTheme.dimens.userLogoSize),
        imageOptions = ImageOptions(
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
    )
}
