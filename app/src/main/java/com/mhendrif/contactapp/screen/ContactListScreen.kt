package com.mhendrif.contactapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mhendrif.contactapp.view.ContactViewModel
import com.mhendrif.contactapp.components.AddContactDialog
import com.mhendrif.contactapp.components.ContactItem
import com.mhendrif.contactapp.components.ContactTopAppBar
import com.mhendrif.contactapp.components.EmptySearchResult

@Composable
fun ContactListScreen(
    viewModel: ContactViewModel,
    onContactClick: (Int) -> Unit,
    onAboutClick: () -> Unit,
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val contacts by viewModel.allContact.observeAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var isSearchActive by remember { mutableStateOf(false) }

    val displayedContacts = if (searchQuery.isEmpty()) contacts else searchResults

    Scaffold(
        topBar = {
            ContactTopAppBar(
                title = "Contacts",
                canNavigateBack = false,
                canSearch = true,
                isSearchActive = isSearchActive,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::searchContacts,
                onSearchActiveChange = { isSearchActive = it },
                actions = {
                    IconButton(onClick = { onAboutClick() }) {
                        Icon(Icons.Default.Person, contentDescription = "About")
                    }
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add contact")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (searchQuery.isNotEmpty() && displayedContacts.isEmpty()) {
            EmptySearchResult(searchQuery)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(displayedContacts) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = { onContactClick(contact.id) }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddContactDialog(
            onDismiss = { showAddDialog = false },
            onAddContact = { name, phone, email ->
                viewModel.addContact(name, phone, email)
                showAddDialog = false
            },
            viewModel = viewModel
        )
    }
}

