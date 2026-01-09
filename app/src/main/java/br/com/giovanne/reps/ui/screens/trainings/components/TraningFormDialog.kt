package br.com.giovanne.reps.ui.screens.trainings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.giovanne.reps.data.Training

@Composable
fun TrainingFormDialog(
    training: Training?,
    onDismiss: () -> Unit,
    onSave: (Training) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (training == null) "Novo Treino" else "Editar Treino") },
        text = {
            TrainingForm(training = training, onSave = onSave)
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TrainingForm(training: Training?, onSave: (Training) -> Unit) {
    var name by remember(training) { mutableStateOf(training?.name ?: "") }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome do Treino") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newOrUpdatedTraining = training?.copy(name = name) ?: Training(
                    name = name,
                    color = 0xFF6200EE, // Default color for new trainings
                    times = emptyList(),
                    exercises = training?.exercises ?: emptyList(), // Preserve exercises on edit
                    current = training?.current ?: false
                )
                onSave(newOrUpdatedTraining)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }
    }
}