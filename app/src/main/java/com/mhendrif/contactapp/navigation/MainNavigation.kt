package com.mhendrif.contactapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mhendrif.contactapp.view.ContactViewModel
import com.mhendrif.contactapp.screen.ContactDetailScreen
import com.mhendrif.contactapp.screen.ContactListScreen

@Composable
fun MainNavigation(modifier: Modifier, viewModel: ContactViewModel, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.ContactList.route,
    ) {
        composable(Screen.ContactList.route) {
            ContactListScreen(
                viewModel = viewModel,
                onContactClick = { contactId ->
                    navController.navigate(Screen.ContactDetail.createRoute(contactId))
                }
            )
        }
        composable(
            route = Screen.ContactDetail.route,
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: return@composable
            ContactDetailScreen(
                viewModel = viewModel,
                contactId = contactId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}