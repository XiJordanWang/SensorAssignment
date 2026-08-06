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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale.getDefault

class SensorActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>
    private var map = mutableStateMapOf<String, FloatArray?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val rainbowColors: List<Color> = listOf(
            Color.Yellow, Color.Cyan, Color.Magenta
        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Get all sensors
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

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

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
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
                                .padding(top = 48.dp, bottom = 12.dp)
                                .fillMaxWidth(),
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    items(sensorList) { sensor ->
                        CardInfo(sensor)
                    }
                }

            }
        }
    }


    override fun onResume() {
        super.onResume()
        // Register the sensor listener
        sensorList.forEach { sensor ->
            run {
                sensor.let {
                    sensorManager.registerListener(
                        this,
                        it,
                        SensorManager.SENSOR_DELAY_NORMAL
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Delete the sensor listener to save battery
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        println("Sensor: ${sensor?.name}");
        if (sensor != null) {
            map[sensor.stringType] = event.values
        }
    }

    fun bindSensor() {
        sensorList.forEach { sensor ->
            map[sensor.stringType] = null
        }
    }

    @Composable
    fun CardInfo(sensor: Sensor) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 18.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),

            ) {
            Text(
                text = sensor.stringType
                    .replace("android.sensor.", "")
                    .uppercase(getDefault())
                    .replace("_", " "),
                fontWeight = FontWeight.Bold, fontSize = 13.sp,
            )
            when (sensor.stringType) {
                Sensor.STRING_TYPE_ACCELEROMETER -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_GYROSCOPE -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_MAGNETIC_FIELD -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_ORIENTATION -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_AMBIENT_TEMPERATURE -> {
                    Text("Temperature: ${map[sensor.stringType]?.get(0)}")
                }

                Sensor.STRING_TYPE_PROXIMITY -> {
                    Text("Proximity: ${map[sensor.stringType]?.get(0)}")
                }

                Sensor.STRING_TYPE_LIGHT -> {
                    Text("Light: ${map[sensor.stringType]?.get(0)}")
                }

                Sensor.STRING_TYPE_PRESSURE -> {
                    Text("Pressure: ${map[sensor.stringType]?.get(0)}")
                }

                Sensor.STRING_TYPE_RELATIVE_HUMIDITY -> {
                    Text("Humidity: ${map[sensor.stringType]?.get(0)}")
                }

                Sensor.STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_ACCELEROMETER_UNCALIBRATED -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_GAME_ROTATION_VECTOR -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_GRAVITY -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_LINEAR_ACCELERATION -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_ROTATION_VECTOR -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }

                Sensor.STRING_TYPE_ORIENTATION -> {
                    Text("X: ${map[sensor.stringType]?.get(0)}")
                    Text("Y: ${map[sensor.stringType]?.get(1)}")
                    Text("Z: ${map[sensor.stringType]?.get(2)}")
                }
            }
        }
    }
}