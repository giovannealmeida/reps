package br.com.giovanne.reps.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.giovanne.reps.data.Training
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun TrainingPage(training: Training, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(16.dp)
    var columnModifier = modifier
        .padding(16.dp)
        .defaultMinSize(minHeight = 400.dp)
        .background(
            MaterialTheme.colorScheme.secondaryContainer,
            shape = shape
        )

    if (training.current) {
        columnModifier = columnModifier.border(4.dp, MaterialTheme.colorScheme.secondary, shape)
    }

    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(training.color), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(24.dp),
                text = training.name, fontSize = 24.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        if (training.times.isEmpty()) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Complete treinos para obter recordes",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            return@Column
        }

        Text("Melhores tempos")
        Spacer(modifier = Modifier.size(16.dp))
        training.times.forEach { timeInMillis ->
            val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
            val formattedTime = String.format(
                Locale.forLanguageTag("pt-BR"),
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            )
            Text(text = formattedTime, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingPagePreview() {
    TrainingPage(
        training = Training("C", 0xFFFF5252, listOf(), listOf(), true),
        modifier = Modifier.fillMaxWidth()
    )
}
