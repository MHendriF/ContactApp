package com.mhendrif.contactapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mhendrif.contactapp.data.local.entity.Contact
import com.mhendrif.contactapp.data.local.dao.ContactDao
import com.mhendrif.contactapp.data.repository.ContactRepository
import com.mhendrif.contactapp.utils.Constants
import com.mhendrif.contactapp.utils.generateDummyContacts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var instance: ContactDatabase? = null
        private var repository: ContactRepository? = null

        private fun getInstance(context: Context): ContactDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            instance?.let { database ->
                                val dao = database.contactDao()
                                val dummyContacts = generateDummyContacts()
                                dummyContacts.forEach { contact ->
                                    dao.insert(contact)
                                }
                            }
                        }
                    }
                })
                .build()
                instance = newInstance
                newInstance
            }
        }

        fun getRepository(context: Context): ContactRepository {
            if (repository == null) {
                val database = getInstance(context)
                repository = ContactRepository(database.contactDao())
            }
            return repository!!
        }
    }
}