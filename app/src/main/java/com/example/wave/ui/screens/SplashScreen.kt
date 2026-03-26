package com.example.wave.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wave.ui.theme.WaveTeal
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable { onNavigateNext() } // Allow tapping to skip
    ) {
        FuturisticSpaceBackground()

        // Central Graphic & Text
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val alphaAnim = remember { Animatable(0f) }
            val scaleAnim = remember { Animatable(0.5f) }
            
            LaunchedEffect(Unit) {
                alphaAnim.animateTo(1f, animationSpec = tween(1500, easing = FastOutSlowInEasing))
            }
            LaunchedEffect(Unit) {
                scaleAnim.animateTo(1f, animationSpec = tween(1500, easing = FastOutSlowInEasing))
            }

            Text(
                text = "WAVE",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp),
                color = WaveTeal.copy(alpha = alphaAnim.value),
                letterSpacing = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "NFC PROTOCOL INIT",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = WaveTeal.copy(alpha = alphaAnim.value * 0.7f),
                letterSpacing = 6.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Composable
fun FuturisticSpaceBackground(modifier: Modifier = Modifier, showRings: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "space_transition")
    
    // Animate rings expanding outwards
    val ringPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_phase"
    )
    
    // Rotate stars
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2f, height / 2f)
        val maxRadius = maxOf(width, height)

        // Draw Concentric Ripples
        if (showRings) {
            val numRings = 6
            for (i in 0 until numRings) {
                val progress = (i.toFloat() / numRings + ringPhase) % 1f
                val radius = progress * maxRadius
                val alpha = (1f - progress) * 0.4f // Fades out as it expands
                
                drawCircle(
                    color = WaveTeal.copy(alpha = alpha),
                    radius = radius,
                    center = center,
                    style = Stroke(width = 2.dp.toPx() + (progress * 4.dp.toPx())) // Thicker as it expands
                )
            }
        }

        // Draw Starfield (dots moving or rotating)
        val numStars = 80
        for (i in 0 until numStars) {
            val angle = (i * 137.5f) + rotationAngle
            val angleRad = angle * (Math.PI / 180f)
            val distanceFactor = ((i * 17) % 100) / 100f
            val distance = distanceFactor * maxRadius
            
            val x = center.x + (cos(angleRad) * distance).toFloat()
            val y = center.y + (sin(angleRad) * distance).toFloat()
            
            val twinkle = (sin(ringPhase * Math.PI * 2 + i) + 1) / 2f
            val alpha = (0.2f + twinkle * 0.8f).toFloat() * distanceFactor
            
            drawCircle(
                color = Color.White.copy(alpha = alpha),
                radius = 1.5.dp.toPx() + (twinkle.toFloat() * 1.dp.toPx()),
                center = Offset(x, y)
            )
        }
    }
}
