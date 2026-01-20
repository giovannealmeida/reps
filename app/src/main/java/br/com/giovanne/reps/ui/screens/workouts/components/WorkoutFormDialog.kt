package br.com.giovanne.reps.ui.screens.workouts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

val predefinedColors = listOf(
    Color(0xFFEF9A9A), // Light Red
    Color(0xFF90CAF9), // Light Blue
    Color(0xFFA5D6A7), // Light Green
    Color(0xFFFFF59D), // Light Yellow
    Color(0xFFCE93D8), // Light Purple
    Color(0xFFFFCC80), // Light Orange
    Color(0xFF80DEEA), // Light Cyan
    Color(0xFFE0E0E0)  // Light Gray
)

@Composable
fun WorkoutFormDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, color: Long) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue("A")) }
    var color by remember { mutableStateOf(predefinedColors.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Novo Workout") },
        text = {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        // Allow only one uppercase character
                        val newText = it.text.uppercase()
                        if (newText.length <= 1) {
                            name = it.copy(text = newText)
                        }
                    },
                    label = { Text("Nome do Workout") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                ColorSelector(
                    selectedColor = color,
                    onColorSelected = { newColor -> color = newColor }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name.text, color.toArgb().toLong()) },
                enabled = name.text.isNotBlank()
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorSelector(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Cor do workout")
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            maxItemsInEachRow = 4
        ) {
            predefinedColors.forEach { color ->
                ColorCell(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
private fun ColorCell(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(48.dp)
            .background(color = color, shape = CircleShape)
            .clickable(onClick = onClick)
            .border(
                width = 3.dp,
                color = if (isSelected) Color.DarkGray else Color.Transparent,
                shape = CircleShape
            )
    )
}
