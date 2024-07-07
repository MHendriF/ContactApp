package com.mhendrif.contactapp.data.repository

import com.mhendrif.contactapp.data.local.dao.ContactDao
import com.mhendrif.contactapp.data.local.entity.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    val allContact: Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun getAllContacts() {
        contactDao.getAllContacts()
    }

    suspend fun searchContacts(query: String) {
        contactDao.searchContacts(query)
    }

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
}