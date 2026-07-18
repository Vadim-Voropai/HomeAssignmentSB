package com.homeassignment.vvoropai.ui.navigation

import com.homeassignment.vvoropai.utils.Constants
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavigationScreens(var screenRoute: String) {
    data object Home : NavigationScreens(Constants.HOME_ROUTES)
    data object UserDetails : NavigationScreens("${Constants.USER_DETAILS_ROUTES}/{repoName}") {
        fun createRoute(url: String): String {
            val repoName = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "${Constants.USER_DETAILS_ROUTES}/$repoName"
        }
    }
}