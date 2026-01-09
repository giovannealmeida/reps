package br.com.giovanne.reps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.giovanne.reps.data.Exercise
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.trainingsMock
import com.example.compose.REPSTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit

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
            Content(
                modifier = Modifier.padding(innerPadding)
            )
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
fun Content(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Date()
        Text("Treino de hoje")
        TrainingViewPager()
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {}) {
            Text("Iniciar")
        }
    }
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
    val exercisesA = listOf(
        Exercise(
            name = "Supino Reto",
            series = 4,
            repetitions = 10,
            load = 80.0f,
            note = "Aumentar a carga na próxima semana."
        ),
        Exercise(
            name = "Crucifixo Inclinado",
            series = 3,
            repetitions = 12,
            load = 30.0f,
            note = "Focar na contração do peitoral."
        ),
        Exercise(
            name = "Desenvolvimento com Halteres",
            series = 4,
            repetitions = 8,
            load = 20.0f,
            note = ""
        ),
        Exercise(
            name = "Elevação Lateral",
            series = 3,
            repetitions = 15,
            load = 10.0f,
            note = "Movimento controlado."
        )
    )

    // Lista de exercícios para o Treino B
    val exercisesB = listOf(
        Exercise(
            name = "Agachamento Livre",
            series = 4,
            repetitions = 8,
            load = 100.0f,
            note = "Manter a postura."
        ),
        Exercise(name = "Leg Press 45", series = 3, repetitions = 10, load = 150.0f, note = ""),
        Exercise(
            name = "Cadeira Extensora",
            series = 3,
            repetitions = 15,
            load = 60.0f,
            note = "Pico de contração."
        ),
        Exercise(
            name = "Mesa Flexora",
            series = 3,
            repetitions = 12,
            load = 50.0f,
            note = "Fase excêntrica lenta."
        )
    )

    // Lista de exercícios para o Treino C
    val exercisesC = listOf(
        Exercise(
            name = "Barra Fixa",
            series = 4,
            repetitions = 5,
            load = 0.0f,
            note = "Usar peso do corpo. Tentar 6 na próxima."
        ),
        Exercise(name = "Remada Curvada", series = 4, repetitions = 8, load = 60.0f, note = ""),
        Exercise(
            name = "Puxada Alta",
            series = 3,
            repetitions = 10,
            load = 55.0f,
            note = "Puxar com as costas."
        ),
        Exercise(name = "Rosca Direta", series = 3, repetitions = 10, load = 20.0f, note = "")
    )

    val trainings = listOf(
        trainingsMock[0].copy(
            times = listOf(630000L, 720000L, 810000L),
            exercises = exercisesA,
            isCurrent = true
        ),
        trainingsMock[1].copy(times = listOf(900000L, 1215000L), exercises = exercisesB),
        trainingsMock[2].copy(exercises = exercisesC),
    )

    val pagerState = rememberPagerState(pageCount = { trainings.size })

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
    ) { page ->
        TrainingPage(training = trainings[page], modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun TrainingPage(training: Training, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(16.dp)
    var columnModifier = modifier
        .padding(16.dp)
        .defaultMinSize(minHeight = 400.dp)
        .background(
            MaterialTheme.colorScheme.secondaryContainer,
            shape = shape
        )

    if (training.isCurrent) {
        columnModifier = columnModifier.border(4.dp, MaterialTheme.colorScheme.secondary, shape)
    }

    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(training.color), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(24.dp),
                text = training.name, fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        if (training.times.isEmpty()) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Complete treinos para obter recordes",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            return@Column
        }

        Text("Melhores tempos")
        Spacer(modifier = Modifier.size(16.dp))
        training.times.forEach { timeInMillis ->
            val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
            val formattedTime = String.format(
                Locale.forLanguageTag("pt-BR"),
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            )
            Text(text = formattedTime, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    REPSTheme {
        Content()
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingPagePreview() {
    TrainingPage(
        training = Training("C", 0xFFFF5252, listOf(), listOf(), true),
        modifier = Modifier.fillMaxWidth()
    )
}
