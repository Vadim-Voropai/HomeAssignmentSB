package com.homeassignment.vvoropai.ui.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.homeassignment.vvoropai.R
import com.homeassignment.vvoropai.ui.components.EmptyView
import com.homeassignment.vvoropai.ui.details.RepoDetailsUiState
import com.homeassignment.vvoropai.ui.theme.dimens

@Composable
fun RepoDetailsView(
    uiState: RepoDetailsUiState,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        if (uiState.isEmpty()) {
            EmptyView(message = stringResource(R.string.empty_list_message))
        } else {
            val repo = uiState.repoDetails!!
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.medium)
            ) {
                Text(
                    text = repo.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))

                if (repo.description.isNotEmpty()) {
                    Text(
                        text = repo.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Stars",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFFFFB300)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
                    Text(
                        text = "${repo.starCount} Stars",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.extraLarge))

                    Icon(
                        imageVector = Icons.Default.ForkRight,
                        contentDescription = "Forks",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small))
                    Text(
                        text = "${repo.forksCount} Forks",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))

                Text(
                    text = "Last updated: ${repo.updatedAt}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                )
            }
        }
    }
}
