package br.com.giovanne.reps.ui.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.getTrainings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrainingViewPager() {
    var trainings by remember { mutableStateOf<List<Training>>(emptyList()) }

    LaunchedEffect(Unit) {
        trainings = getTrainings()
    }

    val initialIndex = trainings.indexOfFirst { it.current }.coerceAtLeast(0)
    val pagerState = rememberPagerState(
        initialPage = initialIndex + 1,
        pageCount = { trainings.size }
    )
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp)
    ) { page ->
        TrainingPage(training = trainings[page], modifier = Modifier.fillMaxWidth())
    }
}