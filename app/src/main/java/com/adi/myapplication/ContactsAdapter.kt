package com.adi.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.time.LocalDate
import java.time.LocalDateTime

class ContactsAdapter(val context: Context, val data: List<Contact>?, val data2: List<String>?): BaseAdapter() {
        var contact_list = 0
    var name: TextView? = null
    var phoneNumber: TextView? = null
    var birthday: TextView? = null
    var months: TextView? = null
    override fun getCount(): Int {
       return if (contact_list == 0) {
           data?.size ?: 0
       } else {
           data2?.size ?: 0
       }
    }

    override fun getItem(position: Int): Any {
        return data?.get(position) ?: 0
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view = if (contact_list == 0) {
              convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.activity_list_view_components, parent, false)
        } else {
             convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.activity_months, parent, false)
        }
         name = view.findViewById<TextView>(R.id.textView_name)
         phoneNumber = view.findViewById<TextView>(R.id.textView_phoneNumber)
        birthday = view.findViewById<TextView>(R.id.textView_birthday)
          months = view.findViewById<TextView>(R.id.months)
        if (contact_list == 0) {
            bindContacts(
                data?.get(position)?.name, data?.get(position)?.phoneNumber,
                data?.get(position)?.birthDay
            )
        } else {
            bindMonths(data2?.get(position)!!)
        }

        return view
    }

    fun bindContacts(name: String?, phoneNumber: String?, birthdayAlarm: LocalDateTime?) {
        this.name?.text = name
        this.phoneNumber?.text = phoneNumber
        this.birthday?.text = birthdayAlarm.toString()
    }

    fun bindMonths(months: String) {
        this.months?.text = months
    }

}