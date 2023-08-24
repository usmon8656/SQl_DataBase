package com.example.sqldatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) , MyDataBaseInterface{

    companion object{
        val DB_NAME = "contact_db"
        val DB_VERSION = 1

        val TABLE_NAME = "contacts_table"
        val CONTACT_ID = "id"
        val CONTACT_NAME ="name"
        val CONTACT_NUMBER = "number"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME($CONTACT_ID integer not null primary key autoincrement , $CONTACT_NAME text not null,$CONTACT_NUMBER text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addContact(contact: Contact) {
        val dataBase = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, contact.name)
        contentValues.put(CONTACT_NUMBER, contact.number)
        dataBase.insert(TABLE_NAME, null , contentValues)
        dataBase.close()
    }

    override fun getAllContacts(): List<Contact> {
        val database = readableDatabase
        val list = ArrayList<Contact>()
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                val contact = Contact(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    number = cursor.getString(2)
                )
                list.add(contact)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editContact(contact: Contact) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_ID,contact.id)
        contentValues.put(CONTACT_NAME,contact.name)
        contentValues.put(CONTACT_NUMBER,contact.number)

        database.update(TABLE_NAME,contentValues,"$CONTACT_ID=?",arrayOf(contact.id.toString()))
    }

    override fun deleteContact(contact: Contact) {
        val database = writableDatabase
        database.delete(TABLE_NAME, "$CONTACT_ID=?" , arrayOf(contact.id.toString()))
    }
}