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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale.getDefault

class SensorActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private lateinit var sensorList: List<Sensor>

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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    sensorList.forEach { sensor ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                sensor.stringType
                                    .replace("android.sensor.", "")
                                    .uppercase(getDefault())
                            )
                        }
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
    }
}