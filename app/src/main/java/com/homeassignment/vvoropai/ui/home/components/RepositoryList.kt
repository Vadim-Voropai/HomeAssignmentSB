package com.homeassignment.vvoropai.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.homeassignment.vvoropai.domain.models.UserRepoModel
import com.homeassignment.vvoropai.ui.theme.dimens

fun LazyListScope.repositoryListItems(
    repos: List<UserRepoModel>,
    onItemClicked: (repoName: String) -> Unit = { _: String -> },
) {
    items(repos) { item ->
        Card(
            shape = RoundedCornerShape(MaterialTheme.dimens.medium),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = MaterialTheme.dimens.cardElevation
            ),
            border = BorderStroke(MaterialTheme.dimens.cardBorder, Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimens.small,
                    end = MaterialTheme.dimens.small,
                    top = MaterialTheme.dimens.medium,
                )
                .clickable {
                    onItemClicked(item.name)
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.large),
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                if (item.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                    )
                }
            }
        }
    }
}
