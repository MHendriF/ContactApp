package com.mhendrif.contactapp.data.repository

import com.mhendrif.contactapp.data.local.dao.ContactDao
import com.mhendrif.contactapp.data.local.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ContactRepository(private val contactDao: ContactDao) {

    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()

    fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts(query)
    }

    suspend fun insert(contact: Contact) {
        withContext(Dispatchers.IO) {
            contactDao.insert(contact)
        }
    }

    suspend fun update(contact: Contact) {
        withContext(Dispatchers.IO) {
            contactDao.update(contact)
        }
    }

    suspend fun delete(contact: Contact) {
        withContext(Dispatchers.IO) {
            contactDao.delete(contact)
        }
    }

    suspend fun getContactById(id: Int): Contact? {
        return withContext(Dispatchers.IO) {
            contactDao.getContactById(id)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            contactDao.deleteAll()
        }
    }
}