package com.homeassignment.vvoropai.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.homeassignment.vvoropai.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    showBackButton: Boolean = false,
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onNavigationIconClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(id = R.color.white),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            MaterialTheme.colorScheme.primary,
            Color.Unspecified,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.onSecondary
        )
    )
}
