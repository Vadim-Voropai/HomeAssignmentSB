package com.homeassignment.vvoropai.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.homeassignment.vvoropai.ui.components.AppTopBar
import com.homeassignment.vvoropai.ui.home.components.HomeView
import com.homeassignment.vvoropai.ui.theme.HomeAssignmentTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNavigationRequested: (repositoryName: String) -> Unit,
) {
    Scaffold(
        topBar = { AppTopBar {} }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HomeView(
                uiState = uiState,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onNavigationRequested = onNavigationRequested
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeAssignmentTheme {
        HomeScreen(
            uiState = HomeUiState(),
            searchQuery = "",
            onSearchQueryChange = {},
        ) { _ -> }
    }
}