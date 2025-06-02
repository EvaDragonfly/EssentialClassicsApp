package com.memoittech.cuviewtv.screens.MoodScreens
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.memoittech.cuviewtv.model.moodList
import com.memoittech.cuviewtv.ui.theme.DarkBg1
import com.memoittech.cuviewtv.ui.theme.DarkBg2
import com.memoittech.cuviewtv.ui.theme.Violet
import kotlin.math.*

@Composable
fun SmoothColorWheel(
    modifier: Modifier = Modifier,
    onSelect : (Int) -> Unit,
    onZoneSelected: (Int) -> Unit = {}
) {
    // Define base colors for the wheel (will create a gradient between them)
    val baseColors = listOf(
        Color.Red,
        Color(0xFFFF00FF), // Magenta
        Color(0xFF800080), // Purple
        Color(0xFF4B0082), // Indigo
        Color.Blue,
        Color.Cyan,
        Color(0xFF008B8B), // Teal
        Color.Green,
        Color(0xFF9ACD32), // Yellow-Green
        Color.Yellow,
        Color(0xFFFF8C00), // Dark Orange
        Color(0xFFFF4500), // Orange-Red
        Color.Red  // Repeat first color to close the loop
    )

    var selectedZone by remember { mutableStateOf(-1) }
    var currentColor by remember { mutableStateOf(Color.Yellow) }
    var selectedAngle by remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            updateSelectedPosition(offset, size, baseColors) { zone, color, angle ->
                                selectedZone = zone
                                selectedAngle = angle
                                currentColor = color
                                onZoneSelected(zone)
                            }
                        },
                        onDrag = { change, _ ->
                            updateSelectedPosition(change.position, size, baseColors) { zone, color, angle ->
                                selectedZone = zone
                                selectedAngle = angle
                                currentColor = color
                                onZoneSelected(zone)
                            }
                        },
                        onDragEnd = {
                            onSelect(selectedZone)
                        }
                    )
                }
        ) {
            val outerRadius = size.minDimension / 2
            val innerRadius = outerRadius * 0.5f
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw smooth color wheel using SweepGradient
            val sweepGradient = Brush.sweepGradient(
                colors = baseColors,
                center = Offset(centerX.toFloat(), centerY.toFloat())
            )

            // Draw the outer circle with the sweep gradient
            drawCircle(
                brush = sweepGradient,
                radius = outerRadius,
                center = Offset(centerX, centerY)
            )

            // Draw inner circle (hole)
            drawCircle(
                color = Color(0xFF880E4F), // Dark magenta background
                radius = innerRadius,
                center = Offset(centerX, centerY)
            )

            // Draw indicator line for selected position
            if (selectedZone != -1) {
                // White arc for selected segment
                rotate(selectedAngle - 15) {
                    drawArc(
                        color = Color.White,
                        startAngle = 0f,
                        sweepAngle = 30f,
                        useCenter = false,
                        topLeft = Offset(centerX - outerRadius, centerY - outerRadius),
                        size = Size(outerRadius * 2, outerRadius * 2),
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
        }

    }
}

private fun updateSelectedPosition(
    position: Offset,
    canvasSize: IntSize,
    baseColors: List<Color>,
    onPositionSelected: (Int, Color, Float) -> Unit
) {
    val centerX = canvasSize.width / 2
    val centerY = canvasSize.height / 2
    val outerRadius = min(canvasSize.width, canvasSize.height) / 2f
    val innerRadius = outerRadius * 0.5f

    // Calculate distance from center
    val dx = position.x - centerX
    val dy = position.y - centerY
    val distance = sqrt(dx * dx + dy * dy)

    // Check if touch is between inner and outer radius
    if (distance >= innerRadius && distance <= outerRadius) {
        // Calculate angle in degrees
        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
        if (angle < 0) angle += 360

        // Convert angle to zone (0-11)
        val zone = ((angle + 15) % 360 / 30).toInt()

        // Calculate interpolated color based on exact angle
        val normalizedAngle = angle / 360f
        val colorIndex = (normalizedAngle * 12).toInt()
        val nextColorIndex = (colorIndex + 1) % 12
        val colorFraction = (normalizedAngle * 12) - colorIndex

        val startColor = baseColors[colorIndex]
        val endColor = baseColors[nextColorIndex]

        val r = lerp(startColor.red, endColor.red, colorFraction)
        val g = lerp(startColor.green, endColor.green, colorFraction)
        val b = lerp(startColor.blue, endColor.blue, colorFraction)

        val interpolatedColor = Color(r, g, b)

        onPositionSelected(zone, interpolatedColor, angle)
    }
}

// Linear interpolation helper function
private fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

@Composable
fun ColorWheelExample(navController: NavController) {

    val colors = listOf(
        Color.Red,
        Color(0xFFFF00FF), // Magenta
        Color(0xFF800080), // Purple
        Color(0xFF4B0082), // Indigo
        Color.Blue,
        Color.Cyan,
        Color(0xFF008B8B), // Teal
        Color.Green,
        Color(0xFF9ACD32), // Yellow-Green
        Color.Yellow,
        Color(0xFFFF8C00), // Dark Orange
        Color(0xFFFF4500), // Orange-Red
    )


    var selectedZone by remember { mutableStateOf(1) }

    var selectedMoodIndex by remember { mutableStateOf(9) }

    val moods  = moodList

    Surface(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(colors[(selectedMoodIndex+11)%12], colors[selectedMoodIndex], colors[(selectedMoodIndex+1)%12])
                    )
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = moods[selectedMoodIndex].title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }

                SmoothColorWheel(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp),
                    onSelect = {
                        navController.navigate("mood_details/${moods[it].id}/${moods[it].title}")
                    },
                    onZoneSelected = {
                        selectedZone = it + 1
                        selectedMoodIndex = it
                    },
                )

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = moods[selectedMoodIndex].title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }

            }
        }
    }
}

//@Preview
//@Composable
//fun PrevMoods(){
//    ColorWheelExample()
//}