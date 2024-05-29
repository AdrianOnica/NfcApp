package com.adi.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.adi.myapplication.MonthsOfYear.listOfMonths
import java.time.LocalDateTime
import java.time.format.DateTimeParseException


class MainActivity : AppCompatActivity() {
    var contactList: ArrayList<Contact> = ArrayList()
    private lateinit var listView: ListView
    private lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        val scheduler = BirthdayAlarm(this)
        getContactList()
        scheduler.schedule(Contact("Stroe", "0741091778", LocalDateTime.now()))
        adapter = ContactsAdapter(this, null, listOfMonths())
        adapter.contact_list = 1
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, i, l ->
            if(adapter.contact_list == 1) {
                adapter = ContactsAdapter(
                    this,
                    contactList.toCelebratedPerson(
                        listOfMonths().nameOfMonthsToIndex(listOfMonths()[i]).toString()
                    ),
                    null
                )
                adapter.contact_list = 0
                listView.adapter =  adapter
            }
        }
    }

    override fun onBackPressed() {
        if(adapter.contact_list == 0) {
            adapter = ContactsAdapter(this, null,  listOfMonths())
            adapter.contact_list = 1
            listView.adapter = adapter
        } else {
            finish()
        }
    }
    @SuppressLint("Range")
    private fun getContactList() {
        val cr = contentResolver
        val listOfBirthdays = mutableListOf<Pair<String, String>>()

        val projection = arrayOf(ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.MIMETYPE
        )

        val selection = "${ContactsContract.Data.MIMETYPE} IN (?, ?)"
        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )

        val cursor = cr.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        if (cursor != null) {
            val mobileNoSet = HashSet<String>()
            try {
                val nameIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
                val data1Index = cursor.getColumnIndex(ContactsContract.Data.DATA1)
                var name: String
                var birthday: Pair<String, String>
                var number: String
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex)
                    when (cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE))) {
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE ->  {
                            birthday = name to cursor.getString(data1Index)
                            listOfBirthdays.add(birthday)
                        }
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                            number = cursor.getString(data1Index)
                            number = number.replace(" ", "")
                            if (!mobileNoSet.contains(number)) {
                                contactList.add(Contact(name, number, null))
                                mobileNoSet.add(number)
                            }
                        }
                    }
                }
            } finally {
                listOfBirthdays.forEach { birthday ->
                    contactList.forEach { contact ->
                        if (birthday.first == contactList[contactList.indexOf(contact)].name)
                            contactList[contactList.indexOf(contact)].birthDay =
                                ("${birthday.second}T00:00:00").toLocalDateTime()
                    }
                }
                cursor.close()
            }
        }
    }
}
fun String.toLocalDateTime(): LocalDateTime {
    return try {
        LocalDateTime.parse(this)
    } catch (e: DateTimeParseException) {
        throw IllegalArgumentException("Invalid date string: $this", e)
    }
}

fun List<Contact>.toCelebratedPerson(monthIndex: String): List<Contact> {
    return this.filter {
        it.birthDay?.monthValue.toString() == monthIndex
    }
}