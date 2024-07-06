package com.mhendrif.contactapp.screen

sealed class Screen(val route: String) {
    object ContactList : Screen("contactList")
    object ContactDetail : Screen("contactDetail/{contactId}") {
        fun createRoute(contactId: Int) = "contactDetail/$contactId"
    }
}