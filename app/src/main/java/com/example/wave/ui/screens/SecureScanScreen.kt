package com.example.wave.ui.screens

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
import com.example.wave.ui.theme.WaveTeal
import com.example.wave.ui.theme.WaveCardBorder
import com.example.wave.ui.theme.WaveSurfaceDark
import com.example.wave.ui.theme.WaveTextSecondary

@Composable
fun SecureScanScreen(
    nfcStateString: String,
    payload: String?,
    errorMessage: String?,
    onNavigateHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SECURE SCAN",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = when {
                errorMessage != null -> "An error occurred. Please try again."
                nfcStateString == "SUCCESS_READ" || nfcStateString == "SUCCESS_WRITE" -> "Success! Tag process completed."
                else -> "Ready to scan. Please hold your device against the NFC tag."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = WaveTextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Status Card
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
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "STATUS",
                    style = MaterialTheme.typography.labelLarge,
                    color = WaveTeal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = nfcStateString,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        if (payload != null || errorMessage != null || nfcStateString == "SUCCESS_WRITE") {
            val isSuccessWrite = nfcStateString == "SUCCESS_WRITE"
            val isError = errorMessage != null
            val borderColor = if (isError) Color(0xFFE57373) else WaveTeal
            val titleText = if (isError) "ERROR" else "INFO ON THE NFC TAG"
            val bodyText = if (isSuccessWrite && errorMessage == null) {
                if (payload != null) "Tag programmed successfully!\n\nHere's what is written on to the NFC tag:\n" + payload else "Tag programmed successfully!\n\nHere's what is written on to the NFC tag."
            } else {
                errorMessage ?: payload ?: "Tag programmed successfully!"
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = borderColor.copy(alpha = 0.4f),
                        ambientColor = borderColor.copy(alpha = 0.1f)
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .background(WaveSurfaceDark)
                    .border(2.dp, borderColor, RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.labelLarge,
                        color = borderColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bodyText,
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                        color = Color.White
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Wait indicator, only show when not finished
        val isFinished = nfcStateString == "SUCCESS_READ" || nfcStateString == "SUCCESS_WRITE" || errorMessage != null
        if (!isFinished) {
            CircularProgressIndicator(
                color = WaveTeal,
                modifier = Modifier.size(48.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(WaveTeal.copy(alpha = 0.1f))
                    .border(2.dp, WaveTeal, RoundedCornerShape(16.dp))
                    .clickable { onNavigateHome() }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BACK TO HOME",
                    style = MaterialTheme.typography.labelLarge,
                    color = WaveTeal,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}
