package com.adi.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Date

@Parcelize
data class Contact(
    var name: String,
    var phoneNumber: String,
    var birthDay: LocalDateTime
): Parcelable
