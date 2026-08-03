package com.example.sensorassignment

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Info(val x: Float, val y: Float, val z: Float, val dimension: Number = 3)

class SensorActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager

    // Accelerometer: Detect rotation, tilt, gravity, and pedometer.
    private var accelerometer: Sensor? = null

    // Gyroscope: Gaming, VR, and camera.
    private var gyroscope: Sensor? = null

    // Magnetometer: Use for compass or navigator.
    private var magnetometer: Sensor? = null

    // Proximity: When we pick up the phone, this sensor will help us turn off the screen.
    private var proximity: Sensor? = null

    // Ambient Light Sensor: This sensor detect the ambient light, mostly using when alter the lightness of the screen.
    private var ambientLight: Sensor? = null

    // Barometer: Mostly using in detecting the floor which the users are.
    private var barometer: Sensor? = null

    private lateinit var sensorListener: SensorEventListener

    private var accelerometerInfo: Info by mutableStateOf(Info(0f, 0f, 0f, 3))
    var gyroscopeInfo by mutableStateOf(Info(0f, 0f, 0f, 3))
    var magnetometerInfo by mutableStateOf(Info(0f, 0f, 0f, 3))
    var proximityInfo by mutableStateOf(Info(0f, 0f, 0f, 1))
    var ambientLightInfo by mutableStateOf(Info(0f, 0f, 0f, 1))
    var barometerInfo by mutableStateOf(Info(0f, 0f, 0f, 1))

    override fun onCreate(savedInstanceState: Bundle?) {
        val rainbowColors: List<Color> = listOf(
            Color.Yellow, Color.Cyan, Color.Magenta
        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getAccelerometerValue()
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.illustration_fitness_equipments_design_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // This makes the image fill the screen
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 16.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                brush = Brush.linearGradient(
                                    colors = rainbowColors
                                ),
                                fontStyle = FontStyle.Italic
                            )
                        ) {
                            append(" Xi Wang's")
                        }
                        append(" assignment!")
                    },
                    color = Color.White,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    fontSize = 32.sp,
                )
                Activities(
                    accelerometerInfo,
                    gyroscopeInfo,
                    magnetometerInfo,
                    proximityInfo,
                    ambientLightInfo,
                    barometerInfo
                )
            }
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
                        accelerometerInfo =
                            Info(event.values[0], event.values[1], event.values[2], 3)
                    }

                    Sensor.TYPE_GYROSCOPE -> {
                        gyroscopeInfo =
                            Info(event.values[0], event.values[1], event.values[2], 3)
                    }

                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        magnetometerInfo =
                            Info(event.values[0], event.values[1], event.values[2], 3)
                    }

                    Sensor.TYPE_PROXIMITY -> {
                        proximityInfo = Info(event.values[0], 0f, 0f, 1)
                    }

                    Sensor.TYPE_LIGHT -> {
                        ambientLightInfo = Info(event.values[0], 0f, 0f, 1)
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
    accelerometer: Info,
    gyroscope: Info,
    magnetometer: Info,
    proximity: Info,
    ambientLight: Info,
    barometer: Info
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),

        ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 172.dp, height = 150.dp)
            ) {
                CardInfo("Accelerometer", accelerometer)
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 172.dp, height = 150.dp)
            ) {
                CardInfo("Gyroscope", gyroscope)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 172.dp, height = 150.dp)
            ) {
                CardInfo("Magnetometer", magnetometer)
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 172.dp, height = 150.dp)
            ) {
                CardInfo("Proximity", proximity)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 172.dp, height = 150.dp)
            ) {
                CardInfo("Ambient Light Sensor", ambientLight)
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 172.dp, height = 150.dp)
            ) {
                CardInfo("Barometer", barometer)
            }
        }
    }
}

@Composable
fun CardInfo(title: String, info: Info) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
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
}

//@Preview(showBackground = true)
//@Composable
//fun ActivitiesPreview() {
//    SensorAssignmentTheme {
//        Activities(accelerateInfo = Info(0f, 0f, 0f))
//    }
//}