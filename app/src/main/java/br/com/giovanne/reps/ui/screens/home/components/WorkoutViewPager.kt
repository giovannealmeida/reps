package br.com.giovanne.reps.ui.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.giovanne.reps.ui.screens.home.HomeScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutViewPager(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val workouts by viewModel.workouts.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = remember(workouts) { workouts.indexOfFirst { it.current }.coerceAtLeast(0) },
        pageCount = { workouts.size }
    )

    LaunchedEffect(Unit) {
        viewModel.reloadUserWorkouts()
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp)
    ) { page ->
        WorkoutPage(workout = workouts[page], modifier = Modifier.fillMaxWidth())
    }
}