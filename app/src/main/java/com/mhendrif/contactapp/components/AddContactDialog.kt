package com.mhendrif.contactapp.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mhendrif.contactapp.data.repository.ContactRepository
import com.mhendrif.contactapp.utils.AppPreview
import com.mhendrif.contactapp.view.ContactViewModel

@Composable
fun AddContactDialog(
    onDismiss: () -> Unit,
    onAddContact: (String, String, String) -> Unit,
    viewModel: ContactViewModel
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val nameError by viewModel.nameError.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            ContactForm(
                name = name,
                phone = phone,
                email = email,
                onNameChange = {
                    name = it
                    viewModel.validateName(it)
                },
                onPhoneChange = {
                    phone = it
                    viewModel.validatePhone(it)
                },
                onEmailChange = {
                    email = it
                    viewModel.validateEmail(it)
                },
                onSave = {
                    if (viewModel.validateContact(name, phone, email)) {
                        onAddContact(name, phone, email)
                        onDismiss()
                    }
                },
                onCancel = onDismiss,
                title = "Add New Contact",
                nameError = nameError,
                phoneError = phoneError,
                emailError = emailError
            )
        }
    }
}