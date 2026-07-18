package com.homeassignment.vvoropai.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.homeassignment.vvoropai.ui.details.RepoDetailsScreen
import com.homeassignment.vvoropai.ui.details.RepoDetailsScreenViewModel
import com.homeassignment.vvoropai.ui.home.HomeScreen
import com.homeassignment.vvoropai.ui.home.HomeScreenViewModel
import com.homeassignment.vvoropai.ui.home.HomeUiState
import com.homeassignment.vvoropai.ui.navigation.NavigationScreens
import com.homeassignment.vvoropai.ui.theme.HomeAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeAssignmentTheme {
                val navController = rememberNavController()
                val searchQuery by homeViewModel.searchQuery.collectAsStateWithLifecycle()
                val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.Home.screenRoute
                ) {
                    composable(NavigationScreens.Home.screenRoute) {
                        HomeScreen(
                            uiState = uiState,
                            searchQuery = searchQuery,
                            onSearchQueryChange = {
                                homeViewModel.updateSearchQuery(it)
                            },
                            onNavigationRequested = { repoName ->
                                navController.navigate(NavigationScreens.UserDetails.createRoute(repoName))
                            },
                        )
                    }
                    composable(
                        route = NavigationScreens.UserDetails.screenRoute,
                        arguments = listOf(
                            navArgument("repoName") { type = NavType.StringType }
                        )
                    ) {
                        val detailsViewModel: RepoDetailsScreenViewModel = hiltViewModel()
                        val detailsState by detailsViewModel.uiState.collectAsStateWithLifecycle()
                        
                        RepoDetailsScreen(
                            uiState = detailsState,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        HomeAssignmentTheme {
            HomeScreen(
                uiState = HomeUiState(),
                searchQuery = "",
                onSearchQueryChange = {},
                onNavigationRequested = { _ -> }
            )
        }
    }
}
