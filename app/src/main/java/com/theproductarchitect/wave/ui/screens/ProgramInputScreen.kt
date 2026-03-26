package com.theproductarchitect.wave.ui.screens

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theproductarchitect.wave.ui.theme.WaveTeal
import com.theproductarchitect.wave.ui.theme.WaveSurfaceDark
import com.theproductarchitect.wave.ui.theme.WaveTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramInputScreen(
    onProgramTap: (String) -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var urlText by remember { mutableStateOf("") }
    
    // According to mockup, the top section has an amber/orange border
    val amberBorder = Color(0xFFFFC107)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .padding(top = 40.dp)
    ) {
        
        // Input Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = amberBorder.copy(alpha = 0.3f),
                    ambientColor = amberBorder.copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(WaveSurfaceDark)
                .border(2.dp, amberBorder, RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "URL",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = WaveTextSecondary,
                    letterSpacing = 2.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = urlText,
                    onValueChange = { urlText = it },
                    placeholder = { 
                        Text("Target URL (e.g., LinkedIn)", color = WaveTextSecondary.copy(alpha = 0.5f)) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = WaveTextSecondary.copy(alpha = 0.2f),
                        unfocusedBorderColor = WaveTextSecondary.copy(alpha = 0.2f),
                        containerColor = Color(0xFF181818),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )
                
                if (urlText.trim().startsWith("http://", ignoreCase = true)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Insecure link detected (HTTP). Click PROGRAM TAG to proceed anyway.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFFC107)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Paste Button
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(24.dp),
                                spotColor = Color.White.copy(alpha = 0.2f),
                                ambientColor = Color.White.copy(alpha = 0.05f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF2A2A2A))
                            .clickable {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val item = clipboard.primaryClip?.getItemAt(0)?.text?.toString()
                                if (!item.isNullOrEmpty()) {
                                    urlText = formatUrl(item)
                                }
                            }
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PASTE",
                            style = MaterialTheme.typography.labelLarge,
                            color = WaveTextSecondary
                        )
                    }

                    // Paste + Clean Button
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(24.dp),
                                spotColor = WaveTeal.copy(alpha = 0.3f),
                                ambientColor = WaveTeal.copy(alpha = 0.1f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .border(1.dp, WaveTeal, RoundedCornerShape(24.dp))
                            .clickable {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val item = clipboard.primaryClip?.getItemAt(0)?.text?.toString()
                                if (!item.isNullOrEmpty()) {
                                    urlText = formatUrl(cleanUrl(item))
                                }
                            }
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PASTE + CLEAN",
                            style = MaterialTheme.typography.labelLarge,
                            color = WaveTeal
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Large Circular Program Button
        val isEmpty = urlText.trim().isEmpty()
        val isHttp = urlText.trim().startsWith("http://", ignoreCase = true)
        val buttonColor = when {
            isEmpty -> Color(0xFF1E1E1E)
            isHttp -> Color(0xFFFFC107)
            else -> WaveTeal
        }
        val textColor = when {
            isEmpty -> WaveTextSecondary.copy(alpha = 0.5f)
            isHttp -> Color.Black
            else -> Color.White
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .shadow(
                        elevation = if (isEmpty) 0.dp else 16.dp,
                        shape = CircleShape,
                        spotColor = buttonColor.copy(alpha = 0.6f),
                        ambientColor = buttonColor.copy(alpha = 0.3f)
                    )
                    .clip(CircleShape)
                    .background(buttonColor)
                    .clickable { 
                        if (!isEmpty) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onProgramTap(formatUrl(urlText))
                        } 
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PROGRAM TAG",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                    color = textColor,
                    letterSpacing = 2.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

fun cleanUrl(rawUrl: String): String {
    return try {
        // Simple regex or query param strip to remove utms.
        var cleaned = rawUrl
        val paramsToRemove = listOf("utm_source", "utm_medium", "utm_campaign", "utm_term", "utm_content", "ref")
        
        if (cleaned.contains("?")) {
            val parts = cleaned.split("?")
            val baseUrl = parts[0]
            val queryParams = parts[1].split("&")
            
            val newParams = queryParams.filterNot { param -> 
                paramsToRemove.any { param.startsWith("$it=") }
            }
            
            cleaned = if (newParams.isEmpty()) {
                baseUrl
            } else {
                "$baseUrl?${newParams.joinToString("&")}"
            }
        }
        cleaned
    } catch (e: Exception) {
        rawUrl
    }
}

fun formatUrl(url: String): String {
    val trimmed = url.trim()
    if (trimmed.isEmpty()) return trimmed
    if (!trimmed.startsWith("http://", ignoreCase = true) && !trimmed.startsWith("https://", ignoreCase = true)) {
        return "https://$trimmed"
    }
    return trimmed
}
