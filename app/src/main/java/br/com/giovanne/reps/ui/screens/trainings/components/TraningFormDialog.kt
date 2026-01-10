package br.com.giovanne.reps.ui.screens.trainings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.ui.components.TrainingSquare
import br.com.giovanne.reps.ui.screens.trainings.TrainingsViewModel

@Composable
fun TrainingSelectDialog(
    viewModel: TrainingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSelect: (Training) -> Unit
) {

    val availableTrainings by viewModel.predefinedTrainings.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecione um treino") },
        text = {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(availableTrainings) { training ->
                    TrainingSquare(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onSelect(training) },
                        training = training
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
