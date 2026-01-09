package br.com.giovanne.reps.ui.screens.trainings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.giovanne.reps.data.Exercise
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExerciseBottomSheet(
    exercises: List<Exercise>,
    onDismiss: () -> Unit,
    onAdd: (Exercise) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        AddExerciseSheetContent(exercises = exercises, sheetState = sheetState) { onAdd(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseSheetContent(
    exercises: List<Exercise>,
    sheetState: SheetState,
    onAdd: (Exercise) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf(Exercise()) }
    var series by remember { mutableStateOf("3") }
    var repetitions by remember { mutableStateOf("10") }
    var load by remember { mutableStateOf("5") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedExercise?.name ?: "",
                onValueChange = {},
                label = { Text("Exercício") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                exercises.forEach { exercise ->
                    DropdownMenuItem(
                        text = { Text(exercise.name) },
                        onClick = {
                            selectedExercise = exercise
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TextField(
                value = series,
                onValueChange = { series = it },
                label = { Text("Séries") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1F)
            )

            TextField(
                value = repetitions,
                onValueChange = { repetitions = it },
                label = { Text("Reps") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1F)
            )

            TextField(
                value = load,
                onValueChange = { load = it },
                label = { Text("Carga") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1F)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onAdd(
                        Exercise(
                            name = selectedExercise.name,
                            series = series.toInt(),
                            repetitions = repetitions.toInt()
                        )
                    )
                }
            }
        }) {
            Text("Adicionar")
        }
    }
}

@Preview
@Composable
fun NewExerciseBottomSheetPreview() {
    NewExerciseBottomSheet(exercises = listOf(), onDismiss = {}) {}
}
