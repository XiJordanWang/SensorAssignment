package com.example.sensorassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sensorassignment.ui.theme.SensorAssignmentTheme

class SensorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Activities()
        }
    }
}


@Composable
fun Activities() {
    Column() {
        for (i in 1..6) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
            ) {
                CardInfo("Sensor$i")
            }
        }
    }
}

@Composable
fun CardInfo(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
    Text(text = "parameter1:")
}

@Preview(showBackground = true)
@Composable
fun ActivitiesPreview() {
    SensorAssignmentTheme {
        Activities()
    }
}