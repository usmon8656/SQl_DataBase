package com.example.sqldatabase

interface MyDataBaseInterface {
    fun addContact(contact: Contact)
    fun getAllContacts():List<Contact>

    fun editContact(contact: Contact)
    fun deleteContact(contact: Contact)
}