package com.example.wave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wave.nfc.NfcState
import com.example.wave.ui.screens.DashboardScreen
import com.example.wave.ui.screens.ProgramFlowScreen
import com.example.wave.ui.screens.ProgramInputScreen
import com.example.wave.ui.screens.SecureScanScreen
import com.example.wave.ui.screens.SplashScreen
import com.example.wave.ui.theme.WaveTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.wave.nfc.NfcManager

/**
 * Entry point for the Wave App. Boots up the Android lifecycle, 
 * initializes Hilt dependency injection, sets up the Compose navigation graph, 
 * and handles foreground app lifecycle binding for the NFC physical hardware.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var nfcManager: NfcManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WaveApp()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcManager.enableReaderMode(this)
    }

    override fun onPause() {
        super.onPause()
        nfcManager.disableReaderMode(this)
    }
}

@Composable
fun WaveApp() {
    val navController = rememberNavController()
    val viewModel: WaveViewModel = hiltViewModel()

    val nfcState by viewModel.nfcState.collectAsState()
    val payload by viewModel.tagPayload.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateNext = { navController.navigate("dashboard") }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onNavigateToProgram = { navController.navigate("program_flow") },
                onNavigateToScan = { 
                    viewModel.startReading()
                    navController.navigate("secure_scan") 
                }
            )
        }
        composable("program_flow") {
            ProgramFlowScreen(
                onNavigateToInput = { navController.navigate("program_input") }
            )
        }
        composable("program_input") {
            ProgramInputScreen(
                onProgramTap = { url ->
                    viewModel.startWriting(url)
                    navController.navigate("secure_scan")
                }
            )
        }
        composable("secure_scan") {
            SecureScanScreen(
                nfcStateString = nfcState.name,
                payload = payload,
                errorMessage = errorMessage,
                onNavigateHome = { 
                    navController.popBackStack("dashboard", inclusive = false)
                }
            )
        }
    }
}
