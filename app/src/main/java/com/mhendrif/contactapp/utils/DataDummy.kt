package com.mhendrif.contactapp.utils

import com.mhendrif.contactapp.data.local.entity.Contact

fun generateDummyContacts(): List<Contact> {
    return listOf(
        Contact(name = "John Doe", phoneNumber = "1234567890", email = "john@example.com"),
        Contact(name = "Jane Smith", phoneNumber = "2345678901", email = "jane@example.com"),
        Contact(name = "Bob Johnson", phoneNumber = "3456789012", email = "bob@example.com"),
        Contact(name = "Alice Brown", phoneNumber = "4567890123", email = "alice@example.com"),
        Contact(name = "Charlie Davis", phoneNumber = "5678901234", email = "charlie@example.com"),
        Contact(name = "Eva Wilson", phoneNumber = "6789012345", email = "eva@example.com"),
        Contact(name = "Frank Miller", phoneNumber = "7890123456", email = "frank@example.com"),
        Contact(name = "Grace Lee", phoneNumber = "8901234567", email = "grace@example.com"),
        Contact(name = "Henry Taylor", phoneNumber = "9012345678", email = "henry@example.com"),
        Contact(name = "Ivy Robinson", phoneNumber = "0123456789", email = "ivy@example.com")
    )
}