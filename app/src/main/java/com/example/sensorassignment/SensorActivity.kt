package com.example.sensorassignment

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getAccelerometerValue()
        setContent {
            Activities()
        }
    }

    // https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion
    private fun getAccelerometerValue() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
}


@Composable
fun Activities() {
    Column(modifier = Modifier.fillMaxSize()) {
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