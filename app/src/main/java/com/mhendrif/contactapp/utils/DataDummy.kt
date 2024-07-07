package com.mhendrif.contactapp.utils

import com.mhendrif.contactapp.data.local.entity.Contact

fun generateDummyContacts(): List<Contact> {
    return listOf(
        Contact(name = "John Doe", phoneNumber = "08123456789", email = "john@example.com"),
        Contact(name = "Jane Smith", phoneNumber = "08234567890", email = "jane@example.com"),
        Contact(name = "Bob Johnson", phoneNumber = "08345678901", email = "bob@example.com"),
        Contact(name = "Alice Brown", phoneNumber = "08456789012", email = "alice@example.com"),
        Contact(name = "Charlie Davis", phoneNumber = "08567890123", email = "charlie@example.com"),
        Contact(name = "Eva Wilson", phoneNumber = "08678901234", email = "eva@example.com"),
        Contact(name = "Frank Miller", phoneNumber = "08789012345", email = "frank@example.com"),
        Contact(name = "Grace Lee", phoneNumber = "08890123456", email = "grace@example.com"),
        Contact(name = "Henry Taylor", phoneNumber = "08901234567", email = "henry@example.com"),
        Contact(name = "Ivy Robinson", phoneNumber = "08901234566", email = "ivy@example.com"),
        Contact(name = "Jack Anderson", phoneNumber = "08901234557", email = "jack@example.com"),

    )
}