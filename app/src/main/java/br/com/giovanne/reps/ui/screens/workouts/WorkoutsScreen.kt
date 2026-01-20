package br.com.giovanne.reps.ui.screens.workouts

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.giovanne.reps.data.Workout
import com.example.compose.REPSTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(
    modifier: Modifier = Modifier, 
    onNewWorkout: () -> Unit = {},
    viewModel: WorkoutsViewModel = hiltViewModel()
) {
    var showWorkoutForm by remember { mutableStateOf(false) }
    var selectedWorkout by remember { mutableStateOf<Workout?>(null) }
    val userWorkouts by viewModel.userWorkouts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadUserWorkouts()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNewWorkout()
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
            items(userWorkouts, key = { it.id }) { workout ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.StartToEnd || it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteWorkout(workout.id)
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color = when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                            SwipeToDismissBoxValue.Settled -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Deletar",
                                tint = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                ) {
                    WorkoutListItem(
                        workout = workout,
                        onClick = {
                            selectedWorkout = workout
                            showWorkoutForm = true
                        }
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun WorkoutListItem(workout: Workout, modifier: Modifier = Modifier, onClick: () -> Unit) {
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
                .background(Color(workout.color)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier.padding(12.dp),
                text = workout.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()) {
            val exercisesCount = workout.exercises.size
            Text(if (exercisesCount == 1) "1 exercício" else "$exercisesCount exercícios")
            Spacer(modifier = Modifier.weight(1f))

            val distinctCategoryNames = workout.exercises
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
fun WorkoutsScreenPreview() {
    REPSTheme {
        WorkoutsScreen()
    }
}
