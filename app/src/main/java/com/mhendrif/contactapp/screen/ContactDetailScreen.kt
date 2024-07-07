package com.mhendrif.contactapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhendrif.contactapp.components.ContactActions
import com.mhendrif.contactapp.components.ContactForm
import com.mhendrif.contactapp.components.ContactInfo
import com.mhendrif.contactapp.components.CustomTopAppBar
import com.mhendrif.contactapp.data.local.entity.Contact
import com.mhendrif.contactapp.ui.theme.BluePrimary
import com.mhendrif.contactapp.utils.showToast
import com.mhendrif.contactapp.view.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    viewModel: ContactViewModel,
    contactId: Int,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val contacts by viewModel.allContact.observeAsState(initial = emptyList())
    val contact = contacts.find { it.id == contactId }
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    var deletedContact by remember { mutableStateOf<Contact?>(null) }

    if (contact == null) {
        Text("Contact not found")
        return
    }

    var editMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(contact.name) }
    var phone by remember { mutableStateOf(contact.phoneNumber) }
    var email by remember { mutableStateOf(contact.email) }
    val nameError by viewModel.nameError.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            val result = snackbarHostState.showSnackbar(
                message = it,
                actionLabel = "Undo",
                duration = SnackbarDuration.Short
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    deletedContact?.let { contact ->
                        viewModel.undoDeleteContact(contact)
                    }
                }
                SnackbarResult.Dismissed -> {
                    viewModel.clearSnackbarMessage()
                    deletedContact = null
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = if (editMode) "Edit Contact" else contact.name,
                canNavigateBack = true,
                navigateUp = onNavigateBack,
                actions = {
                    if (!editMode) {
                        IconButton(onClick = { editMode = true }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Contact"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Contact Avatar
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
                    .background(BluePrimary, CircleShape)
            ) {
                Text(
                    text = contact.name.first().toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (editMode) {
                ContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onSave = {
                        if (viewModel.validateContact(name, phone, email)) {
                            viewModel.updateContact(contact.copy(name = name, phoneNumber = phone, email = email))
                            editMode = false
                        }
                    },
                    onCancel = { editMode = false },
                    title = "Edit Contact",
                    nameError = nameError,
                    phoneError = phoneError,
                    emailError = emailError
                )
            } else {
                ContactInfo(name = name, phone = phone, email = email)
                Spacer(modifier = Modifier.height(24.dp))
                ContactActions(
                    onCall = { showToast(context, "This feature is under development") },
                    onMessage = { showToast(context, "This feature is under development") },
                    onEmail = { showToast(context, "This feature is under development") },
                    onDelete = {
                        deletedContact = contact
                        viewModel.deleteContact(contact)
                        onNavigateBack()
                    }
                )
            }
        }
    }
}