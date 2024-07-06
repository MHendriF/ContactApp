package com.mhendrif.contactapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.mhendrif.contactapp.screen.MainScreen
import com.mhendrif.contactapp.ui.theme.ContactAppTheme
import com.mhendrif.contactapp.view.ContactViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ContactViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = ContactDatabase.getDatabase(applicationContext)
                return ContactViewModel(database.contactDao()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var isLoading by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { isLoading }

        setContent {
            ContactAppTheme {
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    // Simulate some loading or initialization
                    kotlinx.coroutines.delay(3000) // 2 seconds delay, replace with actual initialization if needed
                    isLoading = false
                }

                MainScreen(viewModel, navController)
            }
        }
    }
}