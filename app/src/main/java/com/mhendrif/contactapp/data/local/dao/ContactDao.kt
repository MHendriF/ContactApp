package com.mhendrif.contactapp.data.local.dao

import androidx.room.*
import com.mhendrif.contactapp.data.local.entity.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<Contact>

    @Query("SELECT * FROM contacts WHERE name LIKE :searchQuery OR phoneNumber LIKE :searchQuery OR email LIKE :searchQuery")
    fun searchContacts(searchQuery: String): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)
}