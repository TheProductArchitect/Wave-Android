package com.theproductarchitect.wave.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theproductarchitect.wave.ui.theme.WaveTeal
import com.theproductarchitect.wave.ui.theme.WaveCardBorder
import com.theproductarchitect.wave.ui.theme.WaveSurfaceDark
import com.theproductarchitect.wave.ui.theme.WaveTextSecondary

@Composable
fun DashboardScreen(
    onNavigateToProgram: () -> Unit,
    onNavigateToScan: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Background animation (Ripples disabled to prevent distraction)
        FuturisticSpaceBackground(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            showRings = false
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DashboardCard(
                title = "PROGRAM",
                subtitle = "Write your link onto a blank NFC tag to instantly share it when tapped against any modern smartphone.",
                buttonText = "PROGRAM A TAG",
                onClick = onNavigateToProgram
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            DashboardCard(
                title = "SECURE SCAN",
                subtitle = "Safely format and verify any unknown NFC tag in a restricted sandbox before opening it to protect against malicious payloads.",
                buttonText = "SCAN A TAG",
                onClick = onNavigateToScan
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = WaveTeal.copy(alpha = 0.25f),
                ambientColor = WaveTeal.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(WaveSurfaceDark)
            .border(1.dp, WaveCardBorder, RoundedCornerShape(24.dp))
            .padding(32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 22.sp,
                    letterSpacing = 1.sp
                ),
                color = WaveTextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = WaveTeal.copy(alpha = 0.4f),
                        ambientColor = WaveTeal.copy(alpha = 0.2f)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .border(1.dp, WaveTeal, RoundedCornerShape(16.dp))
                    .clickable { onClick() }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.labelLarge,
                    color = WaveTeal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
