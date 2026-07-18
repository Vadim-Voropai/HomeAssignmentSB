package com.homeassignment.vvoropai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimens(
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val mediumSmall: Dp = 5.dp,
    val medium: Dp = 8.dp,
    val mediumLarge: Dp = 10.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 32.dp,

    // Specific component sizes
    val loadingBarSize: Dp = 60.dp,
    val loadingBarStroke: Dp = 5.dp,
    val cardElevation: Dp = 6.dp,
    val cardBorder: Dp = 0.5.dp,
    val userLogoSize: Dp = 300.dp,

    val appBarTitleSize: TextUnit = 15.sp,

)

val LocalDimens = staticCompositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current
