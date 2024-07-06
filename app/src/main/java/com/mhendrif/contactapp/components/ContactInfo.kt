package com.mhendrif.contactapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable

@Composable
fun ContactInfo(name: String, phone: String, email: String) {
    Column {
        ContactInfoItem(Icons.Default.Person, "Name", name)
        ContactInfoItem(Icons.Default.Phone, "Phone", phone)
        ContactInfoItem(Icons.Default.Email, "Email", email)
    }
}