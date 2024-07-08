package com.mhendrif.contactapp.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mhendrif.contactapp.navigation.MainNavigation
import com.mhendrif.contactapp.view.ContactViewModel

@Composable
fun MainScreen(viewModel: ContactViewModel, navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        MainNavigation(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            navController = navController
        )
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearSnackbarMessage()
        }
    }
}