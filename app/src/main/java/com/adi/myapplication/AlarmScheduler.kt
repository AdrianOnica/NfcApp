package com.adi.myapplication

interface AlarmScheduler {
    fun schedule(date: String)
    fun cancel()
}