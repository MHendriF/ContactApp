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
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.mhendrif.contactapp.data.local.ContactDatabase
import com.mhendrif.contactapp.data.repository.ContactRepository
import com.mhendrif.contactapp.screen.MainScreen
import com.mhendrif.contactapp.ui.theme.ContactAppTheme
import com.mhendrif.contactapp.utils.Constants
import com.mhendrif.contactapp.view.ContactViewModalFactory
import com.mhendrif.contactapp.view.ContactViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var isLoading by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { isLoading }

        val repository = ContactDatabase.getRepository(applicationContext)
        val viewModel: ContactViewModel by viewModels { ContactViewModalFactory((repository)) }

        setContent {
            ContactAppTheme {
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    // Simulate some loading or initialization
                    kotlinx.coroutines.delay(2000) // 2 seconds delay, replace with actual initialization if needed
                    isLoading = false
                }

                MainScreen(viewModel, navController)
            }
        }
    }
}