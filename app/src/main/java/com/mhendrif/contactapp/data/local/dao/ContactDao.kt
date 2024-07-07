package com.mhendrif.contactapp.data.local.dao

import androidx.room.*
import com.mhendrif.contactapp.data.local.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE name LIKE :searchQuery OR phoneNumber LIKE :searchQuery OR email LIKE :searchQuery ORDER BY name ASC")
    fun searchContacts(searchQuery: String): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)
}