package com.mhendrif.contactapp.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddContactDialog(
    onDismiss: () -> Unit,
    onAddContact: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            ContactForm(
                name = name,
                phone = phone,
                email = email,
                onNameChange = { name = it },
                onPhoneChange = { phone = it },
                onEmailChange = { email = it },
                onSave = {
                    onAddContact(name, phone, email)
                    onDismiss()
                },
                onCancel = onDismiss,
                title = "Add New Contact"
            )
        }
    }
}