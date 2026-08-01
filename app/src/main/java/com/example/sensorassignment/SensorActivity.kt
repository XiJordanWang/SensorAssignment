package com.example.sensorassignment

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
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

class AccelerateInfo(val x: Float, val y: Float, val z: Float)

class SensorActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var sensorListener: SensorEventListener

    private var accelerateInfo: AccelerateInfo = AccelerateInfo(0f, 0f, 0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getAccelerometerValue()
        setContent {
            Activities(accelerateInfo)
        }
    }

    // https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion
    private fun getAccelerometerValue() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val xAxis = event.values[0]
                val yAxis = event.values[1]
                val zAxis = event.values[2]

                accelerateInfo = AccelerateInfo(xAxis, yAxis, zAxis)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Handle accuracy changes if needed
            }
        }
    }


    override fun onResume() {
        super.onResume()
        // Register the sensor listener
        accelerometer?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Delete the sensor listener to save battery
        sensorManager.unregisterListener(sensorListener)
    }
}


@Composable
fun Activities(accelerateInfo: AccelerateInfo) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (i in 1..6) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
            ) {
                CardInfo("Accelerometer", accelerateInfo)
            }
        }
    }
}

@Composable
fun <T> CardInfo(title: String, details: T) {
    Text(
        text = title,
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
    when (details) {
        is AccelerateInfo -> {
            Text(text = "X: ${details.x}")
            Text(text = "Y: ${details.y}")
            Text(text = "Z: ${details.z}")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ActivitiesPreview() {
    SensorAssignmentTheme {
        Activities(accelerateInfo = AccelerateInfo(0f, 0f, 0f))
    }
}