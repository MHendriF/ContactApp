package com.mhendrif.contactapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_ai")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    val email: String
)