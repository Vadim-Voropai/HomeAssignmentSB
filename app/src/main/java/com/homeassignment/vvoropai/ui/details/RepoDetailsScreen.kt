package com.homeassignment.vvoropai.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.homeassignment.vvoropai.ui.components.AppTopBar
import com.homeassignment.vvoropai.ui.details.components.RepoDetailsView
import com.homeassignment.vvoropai.ui.theme.HomeAssignmentTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RepoDetailsScreen(
    uiState: RepoDetailsUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                showBackButton = true,
                onNavigationIconClick = onBackClick,
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            RepoDetailsView(
                uiState = uiState,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeAssignmentTheme {
        RepoDetailsScreen(
            uiState = RepoDetailsUiState(),
            onBackClick = {}
        )
    }
}
