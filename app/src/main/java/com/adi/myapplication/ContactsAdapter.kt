package com.adi.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ContactsAdapter(val context: Context, val contactList: List<Contact>): BaseAdapter() {

    override fun getCount(): Int {
        return contactList.size
    }

    override fun getItem(position: Int): Contact {
        return contactList[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity_list_view_components, parent, false)

        val name = view.findViewById<TextView>(R.id.textView_name)
        val phoneNumber = view.findViewById<TextView>(R.id.textView_phoneNumber)
        val birthDay = view.findViewById<TextView>(R.id.textView_birthday)

        val contact = getItem(position)
        name.text = contact.name
        phoneNumber.text = contact.phoneNumber
        birthDay.text = contact.birthDay.toString()
        return view
    }
}