package com.adi.myapplication

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    var contactList: ArrayList<Contact> = ArrayList()
    private lateinit var listView: ListView

    private val PROJECTION = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        val adapter = ContactsAdapter(this, contactList)
        val scheduler = BirthdayAlarm(this)
        getContactList()
        contactList.setContactBirthdaybyMonth()
        scheduler.schedule(Contact("Stroe", "0741091778", LocalDateTime.now()))
        listView.adapter = adapter
    }

    private fun getContactList() {
        val cr = contentResolver
        val cursor = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor != null) {
            val mobileNoSet = HashSet<String>()
            try {
                val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val numberIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                var name: String
                var number: String
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex)
                    number = cursor.getString(numberIndex)
                    number = number.replace(" ", "")
                    if (!mobileNoSet.contains(number)) {
                        contactList.add(Contact(name, number, LocalDateTime.now()))
                        mobileNoSet.add(number)
                    }
                }
            } finally {
                cursor.close()
            }
        }
    }

    private fun ArrayList<Contact>.setContactBirthdaybyMonth() {
        this.forEach { contact ->
            when {
                contact.name.lowercase().startsWith("B") -> {
                }

                contact.name.lowercase().startsWith("C") -> {

                }

                contact.name.lowercase().startsWith("D") -> {
                }

                contact.name.lowercase().startsWith("E") -> {
                }

                contact.name.lowercase().startsWith("F") -> {

                }

                contact.name.lowercase().startsWith("G") -> {

                }

                contact.name.lowercase().startsWith("H") -> {

                }

                contact.name.lowercase().startsWith("I") -> {

                }
            }
        }
    }
}
