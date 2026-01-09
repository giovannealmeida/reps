package br.com.giovanne.reps.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.giovanne.reps.Date
import br.com.giovanne.reps.ui.screens.home.components.TrainingViewPager
import com.example.compose.REPSTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Date()
        Text("Treino de hoje")
        TrainingViewPager()
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {}) {
            Text("Iniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    REPSTheme {
        HomeScreen()
    }
}