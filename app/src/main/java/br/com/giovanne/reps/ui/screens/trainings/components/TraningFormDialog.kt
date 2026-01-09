package br.com.giovanne.reps.ui.screens.trainings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.giovanne.reps.data.Training
import br.com.giovanne.reps.data.repositories.getTrainings
import br.com.giovanne.reps.ui.components.TrainingSquare

@Composable
fun TrainingSelectDialog(
    onDismiss: () -> Unit,
    onSelect: (Training) -> Unit
) {

    var availableTrainings by remember { mutableStateOf<List<Training>>(emptyList()) }

    LaunchedEffect(Unit) {
        availableTrainings = getTrainings()
    }

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
                        name = training.name,
                        color = training.color
                    )
                }
            }
        },
        confirmButton = {
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
