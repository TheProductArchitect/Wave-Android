package com.theproductarchitect.wave.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun ProgramFlowScreen(
    onNavigateToInput: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .padding(top = 40.dp)
    ) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WAVE",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "PROGRAM YOUR NFC NETWORK CARD",
                style = MaterialTheme.typography.bodyMedium,
                letterSpacing = 2.sp,
                color = WaveTextSecondary
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Steps
        StepCard(
            stepNumber = "1",
            title = "PREPARE",
            subtitle = "Ensure your NFC tag is handy.",
            linkText = "Click here for versions that work on this device."
        )
        Spacer(modifier = Modifier.height(16.dp))
        StepCard(
            stepNumber = "2",
            title = "INPUT",
            subtitle = "Copy your URL or use the Share function."
        )
        Spacer(modifier = Modifier.height(16.dp))
        StepCard(
            stepNumber = "3",
            title = "WAVE",
            subtitle = "Hold the card to the top-back of your phone."
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Get Started Button
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
                .background(WaveTeal.copy(alpha = 0.1f)) // A subtle inner background
                .border(2.dp, WaveTeal, RoundedCornerShape(16.dp))
                .clickable { onNavigateToInput() }
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "GET STARTED",
                style = MaterialTheme.typography.labelLarge,
                color = WaveTeal,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun StepCard(
    stepNumber: String,
    title: String,
    subtitle: String,
    linkText: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = WaveTeal.copy(alpha = 0.15f),
                ambientColor = WaveTeal.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(WaveSurfaceDark)
            .border(1.dp, WaveCardBorder, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Column {
            // Circle Number
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, WaveTeal, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber,
                    color = WaveTeal,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = WaveTextSecondary
            )
            
            if (linkText != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = linkText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = WaveTeal
                )
            }
        }
    }
}
