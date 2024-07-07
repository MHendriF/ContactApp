package com.mhendrif.contactapp.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mhendrif.contactapp.data.local.entity.Contact
import com.mhendrif.contactapp.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val allContact: LiveData<List<Contact>> = repository.allContact.asLiveData()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Contact>>(emptyList())
    val searchResults: StateFlow<List<Contact>> = _searchResults.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _phoneError = MutableStateFlow<String?>(null)
    val phoneError: StateFlow<String?> = _phoneError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    fun searchContacts(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isEmpty()) {
                _searchResults.value = allContact.value ?: emptyList()
            } else {
                repository.searchContacts("%$query%").collect { contacts ->
                    _searchResults.value = contacts
                }
            }
            Log.d("ContactViewModel", "Search query: $query")
        }
    }

    fun getAllContacts() {
        viewModelScope.launch {
            repository.getAllContacts()
        }
    }

    fun addContact(name: String, phoneNumber: String, email: String) {
        val newContact = Contact(name = name, phoneNumber = phoneNumber, email = email)
        viewModelScope.launch {
            repository.insert(newContact)
            _snackbarMessage.value = "Contact added"
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.update(contact)
            _snackbarMessage.value = "Contact update"
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
            _snackbarMessage.value = "Contact deleted"
        }
    }

    fun undoDeleteContact(contact: Contact) {
        viewModelScope.launch{
            repository.insert(contact)
            _snackbarMessage.value = "Contact restored"
        }
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    fun validateName(name: String): Boolean {
        return if (name.isBlank()) {
            _nameError.value = "Name cannot be empty"
            false
        } else {
            _nameError.value = null
            true
        }
    }

    fun validatePhone(phone: String): Boolean {
        return if (phone.isBlank()) {
            _phoneError.value = "Phone number cannot be empty"
            false
        } else if (!phone.matches(Regex("^[0-9]{10,12}$"))) {
            _phoneError.value = "Invalid phone number (should be 10-12 digits)"
            false
        } else {
            _phoneError.value = null
            true
        }
    }

    fun validateEmail(email: String): Boolean {
        return if (email.isBlank()) {
            _emailError.value = "Email cannot be empty"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailError.value = "Invalid email format"
            false
        } else {
            _emailError.value = null
            true
        }
    }

    fun validateContact(name: String, phone: String, email: String): Boolean {
        return validateName(name) && validatePhone(phone) && validateEmail(email)
    }
}

class ContactViewModalFactory(private val repository: ContactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}