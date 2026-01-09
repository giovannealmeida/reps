package br.com.giovanne.reps.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrainingSquare(modifier: Modifier = Modifier, name: String, color: Long = 0xFFFFB5A7, size: Int = 120) {
    Box(
        modifier = modifier
            .size(size.dp)
            .background(Color(color), MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 40.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingSquarePreview() {
    TrainingSquare(name = "A")
}
