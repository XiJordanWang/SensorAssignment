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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class Info(val x: Float, val y: Float, val z: Float, val dimension: Number = 3)

class SensorActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager

    // Accelerometer
    private var accelerometer: Sensor? = null

    // Gyroscope
    private var gyroscope: Sensor? = null

    // Magnetometer
    private var magnetometer: Sensor? = null

    // Proximity
    private var proximity: Sensor? = null

    // Ambient Light Sensor
    private var ambientLight: Sensor? = null

    // Barometer
    private var barometer: Sensor? = null

    private lateinit var sensorListener: SensorEventListener

    private var accelerateInfo: Info by mutableStateOf(Info(0f, 0f, 0f, 3))
    var gyroInfo by mutableStateOf(Info(0f, 0f, 0f, 3))
    var magnetInfo by mutableStateOf(Info(0f, 0f, 0f, 3))
    var proxInfo by mutableStateOf(Info(0f, 0f, 0f, 1))
    var lightInfo by mutableStateOf(Info(0f, 0f, 0f, 1))
    var barometerInfo by mutableStateOf(Info(0f, 0f, 0f, 1))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getAccelerometerValue()
        setContent {
            Activities(accelerateInfo, gyroInfo, magnetInfo, proxInfo, lightInfo, barometerInfo)
        }
    }

    // https://developer.android.com/develop/sensors-and-location/sensors/sensors_motion
    private fun getAccelerometerValue() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        ambientLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        accelerateInfo =
                            Info(event.values[0], event.values[1], event.values[2], 3)
                    }

                    Sensor.TYPE_GYROSCOPE -> {
                        gyroInfo =
                            Info(event.values[0], event.values[1], event.values[2], 3)
                    }

                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        magnetInfo = Info(event.values[0], event.values[1], event.values[2], 3)
                    }

                    Sensor.TYPE_PROXIMITY -> {
                        proxInfo = Info(event.values[0], 0f, 0f, 1)
                    }

                    Sensor.TYPE_LIGHT -> {
                        lightInfo = Info(event.values[0], 0f, 0f, 1)
                    }

                    Sensor.TYPE_PRESSURE -> {
                        barometerInfo =
                            Info(event.values[0], 0f, 0f, 1)
                    }
                }
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
        gyroscope?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        magnetometer?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        proximity?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        ambientLight?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        barometer?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // Delete the sensor listener to save battery
        sensorManager.unregisterListener(sensorListener)
    }
}


@Composable
fun Activities(
    accelerate: Info,
    gyro: Info,
    magnet: Info,
    prox: Info,
    light: Info,
    barometer: Info
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            CardInfo("Accelerometer", accelerate)
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            CardInfo("Gyroscope", gyro)
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            CardInfo("Magnetometer", magnet)
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            CardInfo("Proximity", prox)
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            CardInfo("Ambient Light Sensor", light)
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            CardInfo("Barometer", barometer)
        }
    }
}

@Composable
fun CardInfo(title: String, info: Info) {
    Text(
        text = title,
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
    when (info.dimension) {
        3 -> {
            Text(text = "X: ${info.x}", textAlign = TextAlign.Center)
            Text(text = "Y: ${info.y}", textAlign = TextAlign.Center)
            Text(text = "Z: ${info.z}", textAlign = TextAlign.Center)
        }

        1 -> {
            Text(text = "Value: ${info.x}", textAlign = TextAlign.Center)
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun ActivitiesPreview() {
//    SensorAssignmentTheme {
//        Activities(accelerateInfo = Info(0f, 0f, 0f))
//    }
//}