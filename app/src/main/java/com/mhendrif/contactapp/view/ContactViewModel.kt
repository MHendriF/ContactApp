package com.mhendrif.contactapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhendrif.contactapp.data.local.entity.Contact
import com.mhendrif.contactapp.data.local.dao.ContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactDao: ContactDao) : ViewModel() {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            _contacts.value = contactDao.getAllContacts()
        }
    }

    fun searchContacts(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _contacts.value = contactDao.searchContacts("%$query%")
        }
    }

    fun addContact(name: String, phoneNumber: String, email: String) {
        val newContact = Contact(name = name, phoneNumber = phoneNumber, email = email)
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.insertContact(newContact)
            _contacts.value = contactDao.getAllContacts()
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.updateContact(contact)
            _contacts.value = contactDao.getAllContacts()
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.deleteContact(contact)
            _contacts.value = contactDao.getAllContacts()
            _snackbarMessage.value = "Contact deleted"
        }
    }

    fun undoDeleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.insertContact(contact)
            _contacts.value = contactDao.getAllContacts()
            _snackbarMessage.value = "Contact restored"
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }
}