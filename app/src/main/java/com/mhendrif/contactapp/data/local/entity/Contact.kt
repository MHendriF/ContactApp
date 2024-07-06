package com.mhendrif.contactapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mhendrif.contactapp.utils.Constants

@Entity(tableName = Constants.CONTACT_TABLE_NAME)
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phoneNumber: String,
    val email: String
)