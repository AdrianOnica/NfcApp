package com.adi.myapplication

interface AlarmScheduler {
    fun schedule(contact: Contact)
    fun cancel()
}