package br.com.giovanne.reps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.giovanne.reps.data.userTrainingsMock
import br.com.giovanne.reps.ui.screens.history.HistoryScreen
import br.com.giovanne.reps.ui.screens.home.HomeScreen
import br.com.giovanne.reps.ui.screens.trainings.components.TrainingPage
import br.com.giovanne.reps.ui.screens.trainings.TrainingsScreen
import com.example.compose.REPSTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            REPSTheme {
                REPSApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun REPSApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = stringResource(it.labelRes)
                        )
                    },
                    label = { Text(stringResource(it.labelRes)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.FAVORITES -> TrainingsScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.PROFILE -> HistoryScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

enum class AppDestinations(
    val labelRes: Int,
    val icon: ImageVector,
) {
    HOME(R.string.destination_home, Icons.Default.Home),
    FAVORITES(R.string.destination_training, Icons.Default.Favorite),
    PROFILE(R.string.destination_history, Icons.Default.AccountBox),
}

@Composable
fun Date() {
    val currentDay = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern(
        "EEE dd/MM",
        Locale.forLanguageTag("pt-BR")
    )
    val formattedDate = currentDay.format(formatter).uppercase(Locale.getDefault())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = formattedDate, fontSize = 32.sp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrainingViewPager() {
    val userTrainings = userTrainingsMock()
    val pagerState = rememberPagerState(pageCount = { userTrainings.size })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
    ) { page ->
        TrainingPage(training = userTrainings[page], modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    REPSTheme {
        HomeScreen()
    }
}
