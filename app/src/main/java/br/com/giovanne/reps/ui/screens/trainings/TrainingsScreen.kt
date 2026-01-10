package br.com.giovanne.reps.ui.screens.trainings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.repositories.addTraining
import br.com.giovanne.reps.data.repositories.getTrainings
import br.com.giovanne.reps.data.repositories.getUserTrainings
import com.example.compose.REPSTheme
import kotlinx.coroutines.launch

@Composable
fun TrainingsScreen(modifier: Modifier = Modifier, onNewTraining: () -> Unit = {}) {
    var showTrainingForm by remember { mutableStateOf(false) }
    var selectedTraining by remember { mutableStateOf<Training?>(null) }
    var userTrainings by remember { mutableStateOf<List<Training>>(emptyList()) }
    var predefinedTrainings by remember { mutableStateOf<List<Training>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        userTrainings = getUserTrainings()
        predefinedTrainings = getTrainings()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNewTraining()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Treino")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(userTrainings) { training ->
                TrainingListItem(
                    training = training,
                    onClick = {
                        selectedTraining = training
                        showTrainingForm = true
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun TrainingListItem(training: Training, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color(training.color)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier.padding(12.dp),
                text = training.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()) {
            val exercisesCount = training.exercises.size
            Text(if (exercisesCount == 1) "1 exercício" else "$exercisesCount exercícios")
            Spacer(modifier = Modifier.weight(1f))

            val distinctCategoryNames = training.exercises
                .flatMap { it.categories }
                .distinct()
                .joinToString(" - ") { it }

            Text(
                text = distinctCategoryNames,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun TrainingsScreenPreview() {
    REPSTheme {
        TrainingsScreen()
    }
}
