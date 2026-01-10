package br.com.giovanne.reps.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.giovanne.reps.data.Training

@Composable
fun TrainingSquare(modifier: Modifier = Modifier, training: Training? = null, size: Int = 120) {

    val name = training?.name ?: "?"
    val color = training?.color ?: 0x00000000

    Box(
        modifier = modifier
            .size(size.dp)
            .background(Color(color), MaterialTheme.shapes.medium)
            .then(
                if (training == null) {
                    Modifier
                        .padding(4.dp)
                        .drawBehind {
                            val stroke = Stroke(
                                width = 4f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                            drawRoundRect(
                                color = Color.Gray,
                                style = stroke,
                                cornerRadius = CornerRadius(8.dp.toPx())
                            )
                        }
                } else {
                    Modifier
                }
            ),
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
    TrainingSquare()
}
