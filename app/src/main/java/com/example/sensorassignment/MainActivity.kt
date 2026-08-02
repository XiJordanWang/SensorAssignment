package com.example.sensorassignment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.sensorassignment.ui.theme.SensorAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SensorAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize().fillMaxHeight()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val rainbowColors: List<Color> = listOf(
        Color.Red, Color.Yellow, Color.Cyan
    )
    Column() {
        // https://developer.android.com/develop/ui/compose/text/style-text
        Text(
            text = buildAnnotatedString {
                append("Hello, there!\n")
                append("This is")
                withStyle(
                    SpanStyle(
                        brush = Brush.linearGradient(
                            colors = rainbowColors
                        )
                    )
                ) {
                    append(" Xi Wang's ")
                }
                append("assignment!")
            },
            modifier = modifier
        )

        // https://developer.android.com/develop/ui/compose/components/button
        val context = LocalContext.current
        ElevatedButton(onClick = {
            val intent = Intent(context, SensorActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Sensor Activity")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SensorAssignmentTheme {
        Greeting()
    }
}