package br.com.giovanne.reps.ui.screens.trainings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.giovanne.reps.data.Exercise
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.ui.components.TrainingSquare
import br.com.giovanne.reps.ui.screens.trainings.NewTrainingViewModel
import br.com.giovanne.reps.ui.screens.trainings.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTrainingForm(
    training: Training? = null,
    viewModel: NewTrainingViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showTrainingSelector by remember { mutableStateOf(false) }
    var showAddExercise by remember { mutableStateOf(false) }
    var selectedTraining by remember { mutableStateOf(training) }
    val title by remember { mutableStateOf(if (selectedTraining == null) "Novo treino" else "Editar treino") }
    val exercises by viewModel.exercises.collectAsState()
    val selectedExercises = remember {
        mutableStateListOf(*(training?.exercises?.toTypedArray() ?: emptyArray()))
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is UiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.resetUiState()
            }

            is UiState.Success -> {
                onBack()
            }

            else -> Unit
        }
    }

    Scaffold(
        topBar = {
        TopAppBar(title = {
            Text(title)
        }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }, actions = {
            TextButton(
                onClick = {
                    viewModel.saveTraining(
                        selectedTraining,
                        selectedExercises.toList()
                    )
                },
                enabled = uiState !is UiState.Saving
            ) {
                if (uiState is UiState.Saving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("SALVAR", color = MaterialTheme.colorScheme.primary)
                }
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            showAddExercise = true
        }) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar Exercício")
        }
    },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            showTrainingSelector = true
                        },
                    contentAlignment = Alignment.BottomEnd
                ) {
                    TrainingSquare(training = selectedTraining)
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit training"
                    )
                }
                Spacer(modifier = Modifier.size(128.dp))
                if (selectedExercises.isEmpty()) {
                    Text("Adicione exercícios")
                } else {
                    selectedExercises.forEach {
                        ExerciseListItem(it)
                    }
                }
            }
        }
    }

    if (showTrainingSelector) {
        TrainingFormDialog(onDismiss = { showTrainingSelector = false }) { name, color ->
            selectedTraining = Training(name = name, color = color)
            showTrainingSelector = false
        }
    }

    if (showAddExercise) {
        NewExerciseBottomSheet(exercises = exercises, onDismiss = {
            showAddExercise = false
        }) { exercise ->
            selectedExercises.add(exercise)
            showAddExercise = false
        }
    }
}

@Composable
fun ExerciseListItem(exercise: Exercise) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(exercise.name, color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}

@Preview
@Composable
fun NewTrainingFormPreview() {
    val exercisesA = listOf(
        Exercise(
            name = "Supino Reto",
            series = 4,
            repetitions = 10,
            load = 80.0f,
            note = "Aumentar a carga na próxima semana.",
            categories = listOf("Peito", "Ombros", "Triceps")
        ),
        Exercise(
            name = "Crucifixo Inclinado",
            series = 3,
            repetitions = 12,
            load = 30.0f,
            note = "Focar na contração do peitoral.",
            categories = listOf("tPeito")
        ),
        Exercise(
            name = "Desenvolvimento com Halteres",
            series = 4,
            repetitions = 8,
            load = 20.0f,
            note = "",
            categories = listOf("Ombros")
        ),
        Exercise(
            name = "Elevação Lateral",
            series = 3,
            repetitions = 15,
            load = 10.0f,
            note = "Movimento controlado.",
            categories = listOf("Ombros")
        )
    )

    NewTrainingForm(
        training = Training(
            "1",
            "A",
            0xFF448AFF,
            listOf(630000L, 720000L, 810000L),
            exercisesA,
            true
        ), onBack = { })
}
